package org.tzsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.tzsd.dao.callback.HibernateCallback;
import org.tzsd.pojo.User;

/**
 * @description: 用于对User进行基本的数据库操作类
 */
public class UserDAO extends GenericDAO {

    /**
     * @description: 通过id获取对应的User对象
     * @param id
     * @return
     */
    public User getUserById(long id){
        return getById(User.class, id);
    }

    /**
     * @description: 通过用户名获取user
     * @param name 用户名
     * @return 用户实例
     */
    public User getUserByName(final String name){
        return (User) getTemplate().doCall(new HibernateCallback<Object>() {
            @Override
            public Object doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getUserByName");
                query.setParameter(1, name);
                return query.uniqueResult();
            }
        });
    }


}
