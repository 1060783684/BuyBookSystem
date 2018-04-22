package org.tzsd.service;

import org.springframework.stereotype.Service;
import org.tzsd.dao.OrderDAO;
import org.tzsd.dao.UserDAO;
import org.tzsd.pojo.Order;
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
    public List<Order> getOrderList(String username, String status){
        if(username == null || status == null){
            return null;
        }
        return orderDAO.getOrderListByUsernameAndStatus(username, status);
    }
}
