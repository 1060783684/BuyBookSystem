package org.tzsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.tzsd.dao.callback.HibernateCallback;
import org.tzsd.pojo.Manager;

import java.util.List;

/**
 * @description: 管理员数据库操作类
 */
@Repository("managerDao")
public class ManagerDAO extends GenericDAO{

    public Manager getManagerByName(String username){
        return getTemplate().doCall(new HibernateCallback<Manager>() {
            @Override
            public Manager doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getManagerByName");
                query.setParameter("username", username);
                List<Manager> list = query.list();
                Manager manager = null;
                if(list != null && !list.isEmpty()){
                    manager = list.get(0);
                }
                return manager;
            }
        });
    }

}
