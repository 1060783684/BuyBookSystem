package org.tzsd.service;

import org.springframework.stereotype.Service;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.dao.*;
import org.tzsd.pojo.*;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @description: 购买服务类
 */
@Service("buyGoodsService")
public class BuyGoodsService {
    @Resource(name = "orderDao")
    private OrderDAO orderDAO;

    @Resource(name = "userDao")
    private UserDAO userDAO;

    @Resource(name = "storeDao")
    private StoreDAO storeDAO;

    @Resource(name = "goodsDao")
    private GoodsDAO goodsDAO;

    @Resource(name = "addressInfoDao")
    private AddressInfoDAO addressInfoDAO;

    private ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10);

    private static ReentrantLock readyBuylock = new ReentrantLock(); //部分操作需要加锁

    private static ReentrantLock buylock = new ReentrantLock(); //部分操作需要加锁

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public StoreDAO getStoreDAO() {
        return storeDAO;
    }

    public void setStoreDAO(StoreDAO storeDAO) {
        this.storeDAO = storeDAO;
    }

    public GoodsDAO getGoodsDAO() {
        return goodsDAO;
    }

    public void setGoodsDAO(GoodsDAO goodsDAO) {
        this.goodsDAO = goodsDAO;
    }

    public AddressInfoDAO getAddressInfoDAO() {
        return addressInfoDAO;
    }

    public void setAddressInfoDAO(AddressInfoDAO addressInfoDAO) {
        this.addressInfoDAO = addressInfoDAO;
    }

    /**
     * @description: 购买物品service的销毁方法
     */
    @PreDestroy
    public void destory() {
        this.threadPool.shutdown();
    }

    /**
     * @param username  购买宝贝的用户的用户名
     * @param addressId 地址id
     * @param goodsId   宝贝id
     * @param num       个数
     * @return 操作状态
     * @description: 预购买宝贝
     */
    public Object[] readyBuyGoods(String username, String addressId, String goodsId, long num) {
        Object[] objects = new Object[2];
        if (username == null || goodsId == null || num <= 0) {
            objects[0] = JSONProtocolConstance.READY_BUY_FAIL;
            return objects; //操作错误
        }
        User user = getUserDAO().getUserByName(username); //获取用户
        if (user == null) {
            objects[0] = JSONProtocolConstance.READY_BUY_FAIL;
            return objects;//操作错误
        }
        String result = null;
        AddressInfo addressInfo = getAddressInfoDAO().getAddressInfoByIdAndUserId(addressId, user.getId()); //获取地址实例
        if (addressInfo == null) {
            objects[0] = JSONProtocolConstance.READY_BUY_FAIL;
            return objects; //操作错误
        }
        try {
            readyBuylock.lock();
            Goods goods = getGoodsDAO().getGoodsById(goodsId);
            if (goods == null) {
                objects[0] = JSONProtocolConstance.READY_BUY_FAIL;
                return objects; //操作错误
            }
            if (goods.getNum() < num) {
                objects[0] = JSONProtocolConstance.READY_BUY_NUM_NOT_ENG;
                return objects; //库存不足
            }
            Store store = getStoreDAO().getStoreById(goods.getStore_id()); //通过goods也可以取得他的id,这里获取它主要是为了获取发货地址
            if (store == null) {
                objects[0] = JSONProtocolConstance.READY_BUY_FAIL;
                return objects; //操作错误
            }
            //随机生成订单id
            String orderId = UUID.randomUUID().toString();
            while (getOrderDAO().getOrderById(orderId) != null) { //uuid冲突重新获取
                orderId = UUID.randomUUID().toString();
            }
            Order order = new Order(orderId, store.getId(), user.getId(), goodsId, Order.WAIT_PAY,
                    num, store.getAddr(), addressInfo.getAddr(), null, addressInfo.getId(), new Date().getTime());

            //这个地方属于事务操作，同时保存订单信息以及修改商品的剩余个数
            result = getOrderDAO().saveOrderAndUpdateGoodsNum(order, goodsId, num);
            if (result == null) {
                objects[0] = JSONProtocolConstance.READY_BUY_FAIL;
                return objects; //操作错误
            }

            //操作成功,添加一个计时任务，在24小时内不付款就删除订单
            final String id = orderId; //订单id
            threadPool.schedule(new Runnable() {
                @Override
                public void run() {
                    Order order = getOrderDAO().getOrderById(id);
                    //多次重试
                    while (order != null && order.getStatus() == Order.WAIT_PAY) {
                        //删除订单，并修改商品的个数
                        getOrderDAO().deleteOrderByIdAndUpdateGoods(order.getId(), order.getGoods_id(), order.getNumber());
                        order = getOrderDAO().getOrderById(id);
                    }
                }
            }, 24, TimeUnit.HOURS);

            objects[0] = JSONProtocolConstance.READY_BUY_SUCCESS;
            objects[1] = orderId;
            return objects;
        } finally {
            if (readyBuylock.isHeldByCurrentThread()) {
                readyBuylock.unlock();
            }
        }
    }

    /**
     * @param username 用户名
     * @param orderId  订单id
     * @return 成功与否
     * @description: 付款购买物品
     */
    public boolean buyGoods(String username, String orderId) {
        if (username == null || orderId == null) {
            return false;
        }
        User user = getUserDAO().getUserByName(username);
        if (user == null) {
            return false;
        }
        try {
            buylock.lock();
            Order order = getOrderDAO().getOrderByIdAndUserId(orderId, user.getId());
            if (order == null) {
                return false;
            }
            if (order.getStatus() != Order.WAIT_PAY) {
                return false;
            }
            order.setStatus(Order.WAIT_PUB); //修改成待发货

            //添加物品的销量
            Goods goods = getGoodsDAO().getGoodsById(order.getGoods_id());
            goods.setSales_num(goods.getSales_num() + order.getNumber());

            getOrderDAO().merge(order);
        } finally {
            if (buylock.isHeldByCurrentThread()) {
                buylock.unlock();
            }
        }
        return true;
    }

    /**
     * @param username  发货商家的用户名
     * @param orderId   订单id
     * @param expressId 快递单号
     * @return 操作成功与否
     * @description: 发货
     */
    public boolean deliverGoods(String username, String orderId, String expressId) {
        if (username == null || orderId == null || expressId == null) {
            return false;
        }
        User user = getUserDAO().getUserByName(username);
        if (user == null) {
            return false;
        }
        Store store = getStoreDAO().getStoreByUserId(user.getId());
        if (store == null) { //没有店铺
            return false;
        }
        Order order = getOrderDAO().getOrderByIdAndStoreId(orderId, store.getId());
        if (order == null) {
            return false;
        }
        if (order.getStatus() != Order.WAIT_PUB) {
            return false;
        }
        order.setStatus(Order.WAIT_INCOME);
        order.setExpress_id(expressId);
        getOrderDAO().merge(order);
        return true;
    }

    /**
     * @param username 用户名
     * @param orderId  订单id
     * @return 操作结果
     * @description: 收货
     */
    public boolean incomeGoods(String username, String orderId) {
        if (username == null || orderId == null) {
            return false;
        }
        User user = getUserDAO().getUserByName(username);
        if (user == null) {
            return false;
        }
        Order order = getOrderDAO().getOrderByIdAndUserId(orderId, user.getId());
        if (order == null) {
            return false;
        }
        if (order.getStatus() != Order.WAIT_INCOME) {
            return false;
        }
        order.setStatus(Order.WAIT_EVAL);
        getOrderDAO().merge(order);
        return true;
    }
}
