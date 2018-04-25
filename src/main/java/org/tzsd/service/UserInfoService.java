package org.tzsd.service;

import org.springframework.stereotype.Service;
import org.tzsd.dao.UserDAO;
import org.tzsd.dao.UserDetailsInfoDAO;
import org.tzsd.manager.LoginUserManager;
import org.tzsd.pojo.User;
import org.tzsd.pojo.UserDetailsInfo;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @description: 管理用户信息的服务类
 */
@Service("userInfoService")
public class UserInfoService {

    @Resource(name = "userDao")
    private UserDAO userDAO;

    @Resource(name = "userDetailsInfoDAO")
    private UserDetailsInfoDAO userDetailsInfoDAO;

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDetailsInfoDAO getUserDetailsInfoDAO() {
        return userDetailsInfoDAO;
    }

    public void setUserDetailsInfoDAO(UserDetailsInfoDAO userDetailsInfoDAO) {
        this.userDetailsInfoDAO = userDetailsInfoDAO;
    }

    /**
     * @description: 验证用户名密码
     * @param username 用户输入的用户名
     * @param password 用户输入的密码
     * @return
     */
    public String validateUser(final String username, final String password){
        if(username == null || password == null){
            return null;
        }
        User user = userDAO.getUserByName(username);
        if(user == null){
            return null;
        }else {
            String u = user.getName(); //数据库中获取的用户名
            String p = user.getPassword(); //数据库中获取的密码
            if(username.equals(u) && password.equals(p)){
                //注册sessionId
                UUID uuid = UUID.randomUUID();
                String sessionId = uuid.toString() + username;
                LoginUserManager.getInstance().registSession(sessionId, user);
                return sessionId;
            }
        }
        return null;
    }

    /**
     * @description: 通过用户名获取用户实例
     * @param username 输入的用户名
     * @return
     */
    public User getUser(String username){
        if(username == null){
            return null;
        }
        return userDAO.getUserByName(username);
    }

    /**
     * @description: 通过用户名密码创建新的用户实例并保存
     * @param username 用户名
     * @param password 密码
     * @return 保存成功后返回用户id
     */
    public long saveUser(String username, String password){
        long id = Long.valueOf(username);
        User user = new User(id, username, password);
        return (long) userDAO.save(user);
    }

    /**
     * @description: 通过用户名获取用户详细信息实例
     * @param username 用户名
     * @return 若采用非正规手段可能产生异常,在上一层处理
     */
    public UserDetailsInfo searchUserDetailsInfo(String username){
        if(username == null){
            return null;
        }
        long id = Long.valueOf(username);
        return userDetailsInfoDAO.getUserDetailsInfoById(id);
    }
}
