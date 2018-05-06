package org.tzsd.service;

import org.springframework.stereotype.Service;
import org.tzsd.dao.OrderDAO;
import org.tzsd.dao.StoreDAO;
import org.tzsd.dao.UserDAO;
import org.tzsd.pojo.Order;
import org.tzsd.pojo.Store;
import org.tzsd.pojo.User;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 订单服务类
 */
@Service("orderService")
public class OrderService {

    @Resource(name = "orderDao")
    private OrderDAO orderDAO;

    @Resource(name = "userDao")
    private UserDAO userDAO;

    @Resource(name = "storeDao")
    private StoreDAO storeDAO;

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

    public static final int PAGE_SIZE = 8; //物品list一页的大小

    /**
     * @description: 通过订单id获取订单的详细信息,使用用户名保证安全性
     * @param username 用户名
     * @param orderId 订单id
     * @return 订单信息
     */
    public Order getOrderInfo(String orderId, String username){
        if(orderId == null){
            return null;
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return null;
        }
        Order order = null;
        try {
            order = getOrderDAO().getOrderByIdAndUserId(orderId, user.getId());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return order;
    }

    /**
     * @description: 通过用户名和状态获取订单list
     * @param username
     * @param status
     * @return
     */
    public List<Order> getOrderList(String username, int status, int page){
        if(username == null){
            return null;
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return null;
        }
        return orderDAO.getOrderListByUserIdAndStatus(user.getId(), status, page * PAGE_SIZE, PAGE_SIZE);
    }

    /**
     * @description: 通过用户名和状态获取对应用户的订单list
     * @param username
     * @param status
     * @return
     */
    public List<Order> getStoreOrderList(String username, int status, int page){
        if(username == null){
            return null;
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return null;
        }
        Store store = getStoreDAO().getStoreByUserId(user.getId());
        if(store == null){
            return null;
        }
        return orderDAO.getOrderListByStoreIdAndStatus(store.getId(), status, page * PAGE_SIZE, PAGE_SIZE);
    }

    /**
     * @description: 获取订单的页数
     * @param username 用户名
     * @param status 订单状态
     * @return 页数
     */
    public long getOrderListPage(String username, int status){
        if(username == null){
            return 0;
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return 0;
        }
        long count = getOrderDAO().getOrderCountByUsernameAndStatus(user.getId(), status);
        long page = count/PAGE_SIZE;
        if(count % PAGE_SIZE > 0){
            page += 1;
        }
        return page;
    }
}
