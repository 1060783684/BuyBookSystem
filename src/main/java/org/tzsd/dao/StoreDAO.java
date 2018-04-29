package org.tzsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.tzsd.dao.callback.HibernateCallback;
import org.tzsd.pojo.Store;

import java.util.List;

/**
 * @description: 店铺相关的数据库操作
 */
@Repository("storeDao")
public class StoreDAO extends GenericDAO{

    /**
     * @description: 通过商店id获取商店信息
     * @param storeId 商店id
     * @return 商店实例
     */
    public Store getStoreById(long storeId){
        return getById(Store.class, storeId);
    }

    /**
     * @description: 通过用户id查询商店信息
     * @param userId 用户id
     * @return 商店实例
     */
    public Store getStoreByUserId(long userId){
        return (Store)getTemplate().doCall(new HibernateCallback<Object>() {
            @Override
            public Object doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getStoreByUserId");
                query.setParameter("user_id",userId);
                List<Store> list = query.list();
                if(list != null && !list.isEmpty()){
                    return list.get(0);
                }
                return null;
            }
        });
    }
}
