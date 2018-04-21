package org.tzsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.tzsd.dao.callback.HibernateCallback;
import org.tzsd.pojo.ShopCar;

import java.util.List;

/**
 * @description: 购物车相关DAO操作
 */
@Repository("shopCarDao")
public class ShopCarDAO extends GenericDAO{

    /**
     * @description: 通过id获取购物车条目实例
     * @param id 购物车条目id
     * @return 购物车实例
     */
    public ShopCar getShopCarById(String id) {
        return getById(ShopCar.class, id);
    }

    /**
     * @description: 通过用户id获取所有购物车的条目
     * @param id 用户id
     * @return 购物车条目列表
     */
    public List<ShopCar> getShopCarListByUserId(final long id){
        return (List<ShopCar>) getTemplate().doCall(new HibernateCallback<Object>() {
            @Override
            public Object doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getShopCarListByUserId");
                query.setParameter("id",id);
                return query.list();
            }
        });
    }
}
