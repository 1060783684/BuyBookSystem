package org.tzsd.dao;

import org.apache.logging.log4j.core.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.tzsd.dao.callback.HibernateCallback;
import org.tzsd.dao.tmp.HibernateTemplate;
import org.tzsd.dao.tmp.HibernateTemplateImpl;

import java.io.Serializable;

/**
 * @description: 项目中DAO类的基类,存放一些基本操作
 */
public class GenericDAO {
    private HibernateTemplate template;
//    private final Logger logger = new Logger();

    public GenericDAO(){
        template = HibernateTemplateImpl.getInstance();
    }

    /**
     * @description: 持久化一个对象到数据库中
     * @param object 待持久化的对象
     * @return 持久化后的对象id
     */
    public Serializable save(final Object object){
        return (Serializable) getTemplate().doCall(new HibernateCallback<Object>() {
            @Override
            public Object doCall(Session session) throws HibernateException {
                return session.save(object);
            }
        });
    }

    /**
     * @description: 持久化一个对象到数据库中
     * @param object 待持久化的对象
     * @return
     */
    public Object saveOrUpdate(final Object object){
        return (Object) getTemplate().doCall(new HibernateCallback<Object>() {
            @Override
            public Object doCall(Session session) throws HibernateException {
                session.saveOrUpdate(object);
                return null;
            }
        });
    }

    /**
     * @description: 持久化一个对象到数据库中,若此对象在数据库中有id相同的对象,则更新
     * @param object 待持久化的对象
     * @return 持久化后的对象id
     */
    public Object merge(final Object object){
        return getTemplate().doCall(new HibernateCallback<Object>() {
            @Override
            public Object doCall(Session session) throws HibernateException {
                return session.merge(object);
            }
        });
    }

    /**
     * @description: 根据id从数据库中取出一个对象实例,如果未能发现符合条件的记录,方法会抛出一个ObejctNotFoundException
     *               load方法会返回代理对象,此对象只有基本的主键值,直到真正使用非主键属性时才查询数据库
     * @param clazz 对应类型的class实例
     * @param id 指定的id
     * @param <T> 对应的泛型
     * @return 对应的对象
     */
    public <T> T loadById(final Class<T> clazz, final long id){
        return getTemplate().doCall(new HibernateCallback<T>() {
            @Override
            public T doCall(Session session) throws HibernateException {
                return session.load(clazz, id);
            }
        });
    }

    /**
     * @description: 根据id从数据库中取出一个对象实例,如果未能发现符合条件的记录,方法会抛出一个ObejctNotFoundException
     *               load方法会返回代理对象,此对象只有基本的主键值,直到真正使用非主键属性时才查询数据库
     * @param clazz 对应类型的class实例
     * @param id 指定的id
     * @param <T> 对应的泛型
     * @return 对应的对象
     */
    public <T> T loadById(final Class<T> clazz, final String id){
        return getTemplate().doCall(new HibernateCallback<T>() {
            @Override
            public T doCall(Session session) throws HibernateException {
                return session.load(clazz, id);
            }
        });
    }

    /**
     * @description: 根据id从数据库中取出一个对象实例,如果未能发现符合条件的记录则返回null
     * @param clazz 对应类型的class实例
     * @param id 指定的id
     * @param <T> 对应的泛型
     * @return 对应的对象或null
     */
    public <T> T getById(final Class<T> clazz, final long id){
        return getTemplate().doCall(new HibernateCallback<T>() {
            @Override
            public T doCall(Session session) throws HibernateException {
                return session.get(clazz, id);
            }
        });
    }

    /**
     * @description: 根据id从数据库中取出一个对象实例,如果未能发现符合条件的记录则返回null
     * @param clazz 对应类型的class实例
     * @param id 指定的id
     * @param <T> 对应的泛型
     * @return 对应的对象或null
     */
    public <T> T getById(final Class<T> clazz, final String id){
        return getTemplate().doCall(new HibernateCallback<T>() {
            @Override
            public T doCall(Session session) throws HibernateException {
                return session.get(clazz, id);
            }
        });
    }

    public HibernateTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }
}
