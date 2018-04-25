package org.tzsd.manager;

import org.tzsd.pojo.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 登录用户与session管理类
 */
public class LoginUserManager {

    private Map<String, User> users; //sessionId(key) ---> user(value)

    private Map<String, Long> sessions; //sessionId(key) ---> startDate(value)

    private static LoginUserManager inst; //单例中的实例

    private static ReentrantLock lock;

    static{
        lock = new ReentrantLock();
    }

    private LoginUserManager(){
        users = new ConcurrentHashMap<String, User>();
        sessions = new ConcurrentHashMap<String, Long>();
    }

    /**
     * @description: 单例模式中用于获取实例用的方法
     * @return
     */
    public static LoginUserManager getInstance(){
        if(inst == null){
            if(inst == null){
                try{
                    lock.lock();
                    inst = new LoginUserManager();
                }finally {
                    if(lock.isHeldByCurrentThread()){
                        lock.unlock();
                    }
                }
            }
        }
        return inst;
    }

    public Map<String, Long> getSessions() {
        return sessions;
    }

    public void setSessions(Map<String, Long> sessions) {
        this.sessions = sessions;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    /**
     * @description: 注册sessionId和用户实例以及sessionId生成时间
     * @param sessionId sessionId
     * @param user 用户实例
     */
    public void registSession(String sessionId, User user){
        putUser(sessionId, user);
        putSession(sessionId, new Date().getTime());
    }

    /**
     * @description: 注销session
     * @param sessionId
     */
    public void invaldateSession(String sessionId){
        if(users.containsKey(sessionId)) {
            users.remove(sessionId);
            sessions.remove(sessionId);
        }
    }

    /**
     * @description: 注册用户实例与对应的sessionId
     * @param sessionId sessionId
     * @param user 用户实例
     */
    private void putUser(String sessionId, User user){
        users.put(sessionId, user);
    }

    /**
     * @description: 注册用户和对应的生成时间
     * @param sessionId sessionId
     * @param date sessionId生成时间
     */
    private void putSession(String sessionId, long date){
        sessions.put(sessionId, date);
    }
}
