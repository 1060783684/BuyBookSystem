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

    //物品dao

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

    /**
     * @description: 添加购物车条目
     * @param username 用户名
     * @param goods_id 宝贝id
     * @param number 个数
     * @return
     */
    public boolean addShopCar(String username, String goods_id, long number){
        return true;
    }

    /**
     * @description: 删除对应购物车id
     * @param username
     * @param shopCarId 购物车条目id
     * @return
     */
    public boolean deleteShopCar(String username, String shopCarId){
        if(username == null || shopCarId == null){
            return false;
        }
        ShopCar shopCar = getShopCarDAO().getShopCarById(shopCarId);
        if(shopCar == null){
            return false;
        }
        User user = getUserDAO().getUserByName(username);
        long userId = user.getId();
        if(shopCar.getUser_id() != userId) {
            return false;
        }
        getShopCarDAO().deleteShopCarForId(shopCarId);
        return true;
    }
}
