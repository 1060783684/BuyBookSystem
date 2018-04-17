package org.tzsd.service;

import org.springframework.stereotype.Service;
import org.tzsd.dao.ShopCarDAO;
import org.tzsd.dao.UserDAO;
import org.tzsd.pojo.ShopCar;
import org.tzsd.pojo.User;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 购物车相关业务处理类
 */
@Service("shopCarService")
public class ShopCarService {

    @Resource(name = "shopCarDao")
    private ShopCarDAO shopCarDAO;

    @Resource(name = "userDao")
    private UserDAO userDAO;

    public ShopCarDAO getShopCarDAO() {
        return shopCarDAO;
    }

    public void setShopCarDAO(ShopCarDAO shopCarDAO) {
        this.shopCarDAO = shopCarDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * @description: 通过用户名获取用户id
     * @param username
     * @return
     */
    public List<ShopCar> searchShopCarList(String username){
        if(username == null){
            return null;
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return null;
        }
        long user_id = user.getId();
        return getShopCarDAO().getShopCarListByUserId(user_id);
    }
    //2.添加购物车

    //3.从购物车中删除
    public boolean deleteShopCar(String username, String shopCarId){
        if(username == null || shopCarId == null){
            return false;
        }
        ShopCar shopCar = getShopCarDAO().getShopCarById(shopCarId);
        if(shopCar == null){
            return false;
        }
        long userId = Long.valueOf(username);
        if(shopCar.getUser_id() != userId){
            return false;
        }
        //待续:通过sessionId判断是否是正确的用户操作
        return true;
    }
}
