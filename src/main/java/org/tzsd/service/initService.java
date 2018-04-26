package org.tzsd.service;

import org.springframework.stereotype.Service;
import org.tzsd.dao.tmp.HibernateTemplateImpl;
import org.tzsd.manager.session.SessionManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @description: 管理项目全局组件的创建和销毁
 */
@Service("initService")
public class initService {
    /**
     * @description: 初始化session管理器
     */
    @PostConstruct
    public void init(){
        SessionManager.getInstance().init();
        System.out.println("SessionManager init!");
    }

    /**
     * @description: 销毁session管理器,销毁hibernate的session工厂线程
     */
    @PreDestroy
    public void destory(){
        SessionManager.getInstance().destory();
        System.out.println("SessionManager destory!");
        HibernateTemplateImpl.getInstance().destory();
        System.out.println("Hibernate sessionFactory destory!");
    }
}
