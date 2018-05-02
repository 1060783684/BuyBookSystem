package org.tzsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.tzsd.dao.callback.HibernateCallback;
import org.tzsd.pojo.StoreUser;

import java.util.List;

/**
 * @description: 商店申请人信息数据库操作类
 */
@Repository("storeUserDao")
public class StoreUserDAO extends GenericDAO{

    /**
     * @description: 通过id获取商店申请人信息
     * @param id 商店申请人id
     * @return 商店申请人信息
     */
    public StoreUser getStoreUserById(long id){
        return getById(StoreUser.class, id);
    }

    /**
     * @description: 通过商店状态获取商店申请人信息list
     * @param isCheck 商店状态
     * @param pageNum 页号
     * @param pageSize 一页的大小
     * @return 商店申请人信息list
     */
    public List getStoreUserByStoreCheck(final int isCheck, final int pageNum, final int pageSize){
        return getTemplate().doCall(new HibernateCallback<List>() {
            @Override
            public List doCall(Session session) throws HibernateException {
                Query query  = session.getNamedQuery("getStoreUserByStoreCheck");
                query.setParameter("isCheck",isCheck);
                query.setFirstResult(pageNum);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
    }

    /**
     * @description: 通过商店状态获取商店申请人信息条目数
     * @param isCheck 商店状态
     * @return 商店申请人信息条目数
     */
    public long getStoreUserCountByStoreCheck(final int isCheck){
        return getTemplate().doCall(new HibernateCallback<Long>() {
            @Override
            public Long doCall(Session session) throws HibernateException {
                Query query  = session.getNamedQuery("getStoreUserCountByStoreCheck");
                query.setParameter("isCheck",isCheck);
                List<Long> list = query.list();
                long count = 0;
                if(list != null && !list.isEmpty()){
                    count = list.get(0);
                }
                return count;
            }
        });
    }
}
