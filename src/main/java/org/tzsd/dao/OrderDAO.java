package org.tzsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.tzsd.dao.callback.HibernateCallback;
import org.tzsd.pojo.Goods;
import org.tzsd.pojo.Order;

import java.util.List;

/**
 * @description: 订单相关的DAO操作
 */
@Repository("orderDao")
public class OrderDAO extends GenericDAO{

    /**
     * @description: 通过id获取订单实例
     * @param id 订单id
     * @return 订单实例
     */
    public Order getOrderById(String id){
        return getById(Order.class, id);
    }

    /**
     * @description: 通过订单id和用户id获取订单信息
     * @param id 订单id
     * @param userId 用户id
     * @return 订单实例
     */
    public Order getOrderByIdAndUserId(final String id, final long userId){
        return getTemplate().doCall(new HibernateCallback<Order>() {
            @Override
            public Order doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getOrderByIdAndUserId");
                query.setParameter("id", id);
                query.setParameter("user_id", userId);
                return (Order)query.list().get(0);
            }
        });
    }

    /**
     * @description: 通过订单id和用户id获取订单信息
     * @param id 订单id
     * @param storeId 店铺id
     * @return 订单实例
     */
    public Order getOrderByIdAndStoreId(final String id, final long storeId){
        return getTemplate().doCall(new HibernateCallback<Order>() {
            @Override
            public Order doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getOrderByIdAndStoreId");
                query.setParameter("id", id);
                query.setParameter("store_id", storeId);
                return (Order)query.list().get(0);
            }
        });
    }

    /**
     * @description: 通过用户id和状态获取订单list
     * @param userId 用户id
     * @param status 订单状态
     */
    public List<Order> getOrderListByUserIdAndStatus(final long userId, final int status, final int startPage, final int pageSize){
        return getTemplate().doCall(new HibernateCallback<List<Order>>() {
            @Override
            public List<Order> doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getOrderListByUserIdAndStatus");
                query.setParameter("user_id", userId);
                query.setParameter("status", status);
                query.setFirstResult(startPage);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
    }

    /**
     * @description: 通过商店id和状态获取订单list
     * @param storeId 商店id
     * @param status 订单状态
     */
    public List<Order> getOrderListByStoreIdAndStatus(final long storeId, final int status, final int startPage, final int pageSize){
        return getTemplate().doCall(new HibernateCallback<List<Order>>() {
            @Override
            public List<Order> doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getOrderListByStoreIdAndStatus");
                query.setParameter("store_id", storeId);
                query.setParameter("status", status);
                query.setFirstResult(startPage);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
    }

    /**
     * @description: 通过用户名和状态获取订单list
     * @param userId 用户名
     * @param status 订单状态
     */
    public Long getOrderCountByUsernameAndStatus(final long userId, final int status){
        return getTemplate().doCall(new HibernateCallback<Long>() {
            @Override
            public Long doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getOrderCountByUserIdAndStatus");
                query.setParameter("user_id", userId);
                query.setParameter("status", status);
                long count = 0;
                List<Long> list = query.list();
                if(list != null && !list.isEmpty()){
                    count = list.get(0);
                }
                return count;
            }
        });
    }

    /**
     * @description: 保存订单信息,同时修改物品的库存
     * @param order 订单实例
     * @param goodsId 物品id
     * @param num 库存
     */
    public String saveOrderAndUpdateGoodsNum(final Order order, final String goodsId, final long num){
        return getTemplate().doCall(new HibernateCallback<String>() {
            @Override
            public String doCall(Session session) throws HibernateException {
                String orderId = (String) session.save(order);
                Query query = session.getNamedQuery("updateGoodsNumReduce");
                query.setParameter("num", num);
                query.setParameter("id", goodsId);
                query.executeUpdate();
                return orderId;
            }
        });
    }
}
