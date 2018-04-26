package org.tzsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.tzsd.dao.callback.HibernateCallback;
import org.tzsd.pojo.User;
import org.tzsd.pojo.UserDetailsInfo;

/**
 * @description: 用于对User进行基本的数据库操作类
 */
@Repository("userDao")
public class UserDAO extends GenericDAO {

    /**
     * @description: 通过id获取对应的User对象
     * @param id 用户id
     * @return 用户实例
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
                query.setParameter("name", name);
                return query.uniqueResult();
            }
        });
    }

    /**
     * @description: 保存用户信息和用户附加信息
     * @param user
     * @param userExt
     * @return
     */
    public long saveUser(final User user, final UserDetailsInfo userExt){
        return (long) getTemplate().doCall(new HibernateCallback<Object>() {
            @Override
            public Object doCall(Session session) throws HibernateException {
                long userid = (Long) session.save(user);
                session.save(userExt);
                return userid;
            }
        });
    }
}
