package org.tzsd.dao.callback;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @description: HibernateTemplate中使用到的回调方法类
 */
public interface HibernateCallback<T> {
    /**
     * @description: 回调方法接口
     * @param session
     * @return
     */
    T doCall(Session session) throws HibernateException;
}
