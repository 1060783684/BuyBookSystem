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
    public static final int MAN = 1;
    public static final int WOMAN = 2;

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
     * @param username 用户输入的用户名
     * @param password 用户输入的密码
     * @return
     * @description: 验证用户名密码
     */
    public User validateUser(final String username, final String password) {
        if (username == null || password == null) {
            return null;
        }
        User user = userDAO.getUserByName(username);
        if (user == null) {
            return null;
        } else {
            String u = user.getName(); //数据库中获取的用户名
            String p = user.getPassword(); //数据库中获取的密码
            if (username.equals(u) && password.equals(p)) {
                return user;
            }
        }
        return null;
    }

    /**
     * @param username 输入的用户名
     * @return
     * @description: 通过用户名获取用户实例
     */
    public User getUser(String username) {
        if (username == null) {
            return null;
        }
        return userDAO.getUserByName(username);
    }

    /**
     * @param username 用户名
     * @param password 密码
     * @return 保存成功后返回用户id
     * @description: 通过用户名密码创建新的用户实例并保存
     */
    public long regist(final String username, final String password) {
        long id = Long.valueOf(username);
        User user = new User(id, username, password);
        UserDetailsInfo userExt = new UserDetailsInfo(id, null, null, null, null, null);
        return (long) userDAO.saveUser(user, userExt);
    }

    /**
     * @param id        用户id
     * @param name      名字
     * @param id_number 身份证号
     * @param phone     电话号
     * @param sex       性别
     * @return
     * @description: 保存用户附加信息
     */
    public void updateUserDetailsInfo(long id, String name, String id_number, String phone, int sex) {
        UserDetailsInfo userExt = getUserDetailsInfoDAO().getUserDetailsInfoById(id);

        if (name != null && name.length() < 20) {
            userExt.setName(name);
        }
        if (id_number != null && id_number.length() < 30) {
            userExt.setId_number(id_number);
        }
        if (phone != null && phone.length() <= 11) {
            userExt.setPhone(phone);
        }
        if (sex == MAN) {
            userExt.setSex("MAN");
        }
        if (sex == WOMAN) {
            userExt.setSex("WOMAN");
        }

        getUserDetailsInfoDAO().merge(userExt);
    }

    /**
     * @param username 用户名
     * @return 若采用非正规手段可能产生异常, 在上一层处理
     * @description: 通过用户名获取用户详细信息实例
     */
    public UserDetailsInfo searchUserDetailsInfo(String username) {
        if (username == null) {
            return null;
        }
        User user = getUserDAO().getUserByName(username);
        if (user == null) {
            return null;
        }
        return userDetailsInfoDAO.getUserDetailsInfoById(user.getId());
    }
}
