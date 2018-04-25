package org.tzsd.manager.session;

import org.tzsd.manager.LoginUserManager;
import org.tzsd.manager.session.task.SessionManagerTask;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: sessionId失效管理类
 */
public class SessionManager {
    public static final long SECOND = 1000;
    public static final long MINUTE = SECOND * 60;
    public static final long HOUR = MINUTE * 60;

    private static SessionManager inst;
    private static ReentrantLock lock;

    private LoginUserManager loginUserManager;
    private Timer timer;

    static{
        lock = new ReentrantLock();
    }

    private SessionManager(){
        this.loginUserManager = LoginUserManager.getInstance();
        this.timer = new Timer();
    }

    public static SessionManager getInstance(){
        if(inst == null){
            if(inst == null){
                try{
                    lock.lock();
                    inst = new SessionManager();
                }finally {
                    if(lock.isHeldByCurrentThread()){
                        lock.unlock();
                    }
                }
            }
        }
        return inst;
    }

    public void init(){
        TimerTask task = new SessionManagerTask(loginUserManager);
        this.timer.schedule(task,MINUTE * 1);
    }
}
