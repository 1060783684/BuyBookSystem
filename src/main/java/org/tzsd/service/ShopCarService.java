package org.tzsd.service;

import org.springframework.stereotype.Service;
import org.tzsd.dao.GoodsDAO;
import org.tzsd.dao.ShopCarDAO;
import org.tzsd.dao.UserDAO;
import org.tzsd.pojo.Goods;
import org.tzsd.pojo.ShopCar;
import org.tzsd.pojo.User;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @description: 购物车相关业务处理类
 */
@Service("shopCarService")
public class ShopCarService {

    @Resource(name = "shopCarDao")
    private ShopCarDAO shopCarDAO;

    @Resource(name = "userDao")
    private UserDAO userDAO;

    @Resource(name = "goodsDao")
    private GoodsDAO goodsDAO;

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

    public GoodsDAO getGoodsDAO() {
        return goodsDAO;
    }

    public void setGoodsDAO(GoodsDAO goodsDAO) {
        this.goodsDAO = goodsDAO;
    }

    public static final int PAGE_SIZE = 8;

    /**
     * @description: 通过用户名获取用户id
     * @param username
     * @return
     */
    public List searchShopCarList(String username, int page){
        if(username == null){
            return null;
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return null;
        }
        long user_id = user.getId();
        return getShopCarDAO().getShopCarListByUserId(user_id, page * PAGE_SIZE, PAGE_SIZE);
    }

    /**
     * @description: 返回对应用户的购物车条目页数
     * @param username 用户名
     * @return 页数
     */
    public long getShopCarListSize(String username){
        if(username == null){
            return 0;
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return 0;
        }
        long count = getShopCarDAO().getShopCarCountByUserId(user.getId());
        long page = count/PAGE_SIZE;
        if(count % PAGE_SIZE > 0){
            page += 1;
        }
        return page;
    }

    /**
     * @description: 添加购物车条目
     * @param username 用户名
     * @param goods_id 宝贝id
     * @param number 个数
     * @return
     */
    public boolean addShopCar(String username, String goods_id, long number){
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return false;
        }
        Goods goods = getGoodsDAO().getGoodsById(goods_id);
        if(goods == null){
            return false;
        }
        UUID uuid = null;
        do {
            uuid = UUID.randomUUID();
        }while (getShopCarDAO().getShopCarById(uuid.toString()) != null);
        ShopCar shopCar = new ShopCar(uuid.toString(), goods_id, user.getId(), number);
        try {
            String shopCarId = getShopCarDAO().saveShopCar(shopCar);
            if(shopCarId == null || !shopCarId.equals(uuid.toString())){
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
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
