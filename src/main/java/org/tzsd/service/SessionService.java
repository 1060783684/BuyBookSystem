package org.tzsd.service;

import org.tzsd.dao.UserDAO;
import org.tzsd.manager.LoginUserManager;
import org.tzsd.pojo.User;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @description: session服务类
 */
public class SessionService {

    @Resource(name = "userDao")
    private UserDAO userDAO;

    /**
     * @description: 判断这个session是不是这个用户的
     * @param sessionId
     * @param username 用户名
     * @return session是不是这个用户的
     */
    public boolean isUser(String sessionId, String username){
        if(sessionId == null || username == null){
            return false;
        }
        LoginUserManager instance = LoginUserManager.getInstance();
        if(instance == null){
            return false;
        }
        Map<String, User> users = instance.getUsers();
        if(users == null){
            return false;
        }
        User user = users.get(sessionId);
        if(user == null){
            return false;
        }
        if(!username.equals(user.getName())){
            return false;
        }
        return true;
    }

    /**
     * @description: 注册session
     * @param sessionId
     * @param username 用户名
     */
    public void registSession(String sessionId, String username){
        if(sessionId == null || username == null){
            return;
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return;
        }
        LoginUserManager instance = LoginUserManager.getInstance();
        if(instance == null){
            return;
        }
        instance.registSession(sessionId, user);
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
