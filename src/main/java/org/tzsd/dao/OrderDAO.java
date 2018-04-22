package org.tzsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.tzsd.dao.callback.HibernateCallback;
import org.tzsd.pojo.Order;

import java.util.List;

/**
 * @description: 订单相关的DAO操作
 */
@Repository("orderDao")
public class OrderDAO extends GenericDAO{

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
     * @description: 通过用户名和状态获取订单list
     * @param username 用户名
     * @param status 订单状态
     */
    public List<Order> getOrderListByUsernameAndStatus(final String username, final String status){
        return getTemplate().doCall(new HibernateCallback<List<Order>>() {
            @Override
            public List<Order> doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getOrderListByUsernameAndStatus");
                query.setParameter("username", username);
                query.setParameter("status", status);
                return query.list();
            }
        });
    }

}
