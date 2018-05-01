package org.tzsd.dao.tmp;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.tzsd.dao.callback.HibernateCallback;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: hibernate数据库操作的模板实例
 */
public class HibernateTemplateImpl implements HibernateTemplate{
    private SessionFactory sessionFactory;
    private static ReentrantLock lock;
    private volatile static HibernateTemplate inst;

    static{
        lock = new ReentrantLock();
    }

    private HibernateTemplateImpl(){
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    /**
     * @description: 采用单例模型
     * @return
     */
    public static HibernateTemplate getInstance(){
        if(inst == null){
            try {
                lock.lock();
                if (inst == null) {
                    inst = new HibernateTemplateImpl();
                }
            }finally {
                if(lock.isHeldByCurrentThread()){
                    lock.unlock();
                }
            }
        }
        return inst;
    }

    /**
     * @description: 数据库操作的模板方法,通过传入执行数据库操作回调方法执行操作
     * @param callback 数据库操作回调方法接口实例
     * @param <T>
     * @return
     */
    public <T> T doCall(HibernateCallback<T> callback) {
        T result = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            result = callback.doCall(session);
            transaction.commit();
        } catch (HibernateException e){
            result = null;
            if(transaction != null){
                transaction.rollback();
            }
            //错误日志
            e.printStackTrace();
        } catch (Exception e){
            result = null;
            if(transaction != null){
                transaction.rollback();
            }
            //错误日志
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    /**
     * @description: 关闭session工厂线程
     */
    @Override
    public void destory(){
        this.sessionFactory.close();
    }
}
