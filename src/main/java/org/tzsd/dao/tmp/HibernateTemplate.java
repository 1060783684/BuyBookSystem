package org.tzsd.dao.tmp;

import org.tzsd.dao.callback.HibernateCallback;

/**
 * @description: hibernate数据库操作的模板
 */
public interface HibernateTemplate {

    <T> T doCall(HibernateCallback<T> callback);
}
