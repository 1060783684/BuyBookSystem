package org.tzsd.manager.session.task;

import org.tzsd.manager.LoginUserManager;
import org.tzsd.manager.session.SessionManager;

import java.util.Date;
import java.util.Map;
import java.util.TimerTask;

/**
 * @description: session管理任务
 */
public class SessionManagerTask extends TimerTask {
    private LoginUserManager loginUserManager;

    public SessionManagerTask(LoginUserManager loginUserManager){
        this.loginUserManager = loginUserManager;
    }

    /**
     * @description: session管理任务，用于判断session过期时间，暂时保存session一个小时
     */
    @Override
    public void run() {
        Map<String,Long> sessionMap = loginUserManager.getSessions();
        if(sessionMap != null && !sessionMap.isEmpty()) {
            for (String session : sessionMap.keySet()) {
                if (new Date().getTime() - sessionMap.get(session) > SessionManager.HOUR) {
                    sessionMap.remove(session);
                }
            }
        }
    }
}
