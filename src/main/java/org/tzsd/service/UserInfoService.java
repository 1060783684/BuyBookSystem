package org.tzsd.service;

import org.springframework.stereotype.Service;
import org.tzsd.dao.UserDAO;
import org.tzsd.pojo.User;

import javax.annotation.Resource;

/**
 * @description: 管理用户信息的服务类
 */
@Service("userInfoService")
public class UserInfoService {

    @Resource(name = "userDao")
    UserDAO userDAO;

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * @description: 验证用户名密码
     * @param username 用户输入的用户名
     * @param password 用户输入的密码
     * @return
     */
    public boolean validateUser(final String username, final String password){
        if(username == null || password == null){
            return false;
        }
        User user = userDAO.getUserByName(username);
        if(user == null){
            return false;
        }else {
            String u = user.getName(); //数据库中获取的用户名
            String p = user.getPassword(); //数据库中获取的密码
            if(username.equals(u) && password.equals(p)){
                return true;
            }
        }
        return false;
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

    public int saveUser(String username, String password){
        Integer id = Integer.valueOf(username);
        User user = new User(id, username, password);
        return (int) userDAO.save(user);
    }
}
