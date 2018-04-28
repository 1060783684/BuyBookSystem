package org.tzsd.service;

import org.springframework.stereotype.Service;
import org.tzsd.dao.StoreDAO;
import org.tzsd.dao.UserDAO;
import org.tzsd.pojo.Store;
import org.tzsd.pojo.User;

import javax.annotation.Resource;

/**
 * @description: 店铺相关操作
 */
@Service("storeService")
public class StoreService {
    @Resource(name = "userDao")
    private UserDAO userDAO;

    @Resource(name = "storeDao")
    private StoreDAO storeDAO;

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public StoreDAO getStoreDAO() {
        return storeDAO;
    }

    public void setStoreDAO(StoreDAO storeDAO) {
        this.storeDAO = storeDAO;
    }

    /**
     * @description: 通过用户名获取商店实例
     * @param username 用户名
     * @return 商店实例
     */
    public Store getStore(String username){
        if(username == null){
            return null;
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return null;
        }
        return getStoreDAO().getStoreByUserId(user.getId());
    }
}
