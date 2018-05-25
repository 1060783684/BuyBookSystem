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

    /**
     * @description: 根据检查状态获取store list
     * @param isCheck 状态
     * @return 商店list
     */
    public List<Store> getStoreListByCheck(final int isCheck, final int startPage, final int pageSize){
        return getTemplate().doCall(new HibernateCallback<List<Store>>() {
            @Override
            public List<Store> doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getStoreListByCheck");
                query.setParameter("isCheck",isCheck);
                query.setFirstResult(startPage);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
    }

    /**
     * @description: 返回对应检查状态的商店个数
     * @param isCheck
     * @return
     */
    public long getStoreCountByCheck(final int isCheck){
        return getTemplate().doCall(new HibernateCallback<Long>() {
            @Override
            public Long doCall(Session session) throws HibernateException {
                long count = 0;
                Query query = session.getNamedQuery("getStoreCountByCheck");
                query.setParameter("isCheck",isCheck);
                List<Long> list = query.list();
                if(list != null && !list.isEmpty()){
                    count = list.get(0);
                }
                return count;
            }
        });
    }

    /**
     * @description: 根据店铺id和状态删除店铺
     * @return
     */
    public Object deleteStoreByIdAndStatus(final long id, final int status){
        return getTemplate().doCall(new HibernateCallback<Object>() {
            @Override
            public Object doCall(Session session) throws HibernateException {
                long count = 0;
                Query query = session.getNamedQuery("deleteStoreUserById");
                query.setParameter("storeId",id);
                query.executeUpdate();
                query = session.getNamedQuery("deleteStoreByIdAndStatus");
                query.setParameter("id",id);
                query.setParameter("isCheck",status);
                Object result = query.executeUpdate();
                return result;
            }
        });
    }
}
