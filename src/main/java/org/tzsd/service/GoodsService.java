package org.tzsd.service;

import org.springframework.stereotype.Service;
import org.tzsd.dao.GoodsDAO;
import org.tzsd.dao.UserDAO;
import org.tzsd.pojo.Goods;
import org.tzsd.pojo.User;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 物品相关的服务类
 */
@Service("goodsService")
public class GoodsService {
    public static final int COST_ONE = 1; //价格范围1: cost <= x1
    public static final int COST_TWO = 2; //价格范围2： x1 < cost <= x2
    public static final int COST_THREE = 3; //价格范围3: x2 < cost <= x3
    public static final int COST_FOUR = 4; //价格范围3: x3 < cost <= x4
    public static final int COST_FIVE = 5; //价格范围3: cost >= x5

    public static final int PAGE_SIZE = 8; //物品list一页的大小

    @Resource(name = "goodsDao")
    private GoodsDAO goodsDAO;

    @Resource(name = "userDao")
    private UserDAO userDAO;

    public GoodsDAO getGoodsDAO() {
        return goodsDAO;
    }

    public void setGoodsDAO(GoodsDAO goodsDAO) {
        this.goodsDAO = goodsDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * @description: 查询物品
     * @param type 物品类型
     * @param cost 价格
     * @param keywords 关键字
     * @param page 分页开始的页号
     * @return
     */
    public List<Goods> searchGoods(int type, int cost, String keywords, int page){
        int low = -1;
        int hight = low;
        switch (cost){
            case COST_ONE:
                low = 50;
                break;
            case COST_TWO:
                low = 50;
                hight = 100;
                break;
            case COST_THREE:
                low = 100;
                hight = 200;
                break;
            case COST_FOUR:
                low = 200;
                hight = 300;
                break;
            case COST_FIVE:
                hight = 300;
                break;
        }
        List<Goods> results = getGoodsDAO().getGoodsList(type, low, hight, keywords, page, PAGE_SIZE);
        return results;
    }

    /**
     * @description: 通过物品id获取物品的详细信息
     * @param goodsId 物品id
     * @return 物品实例
     */
    public Goods getGoodsInfo(String goodsId){
        if(goodsId == null){
            return null;
        }
        return getGoodsDAO().getGoodsById(goodsId);
    }

    /**
     * @description: 修改id对应的物品的状态信息
     * @param username 用户名
     * @param id　物品id
     * @return
     */
//    public boolean downGoods(String username, String id){
//        if(username == null || id == null){
//            return false;
//        }
//        User user = getUserDAO().getUserByName(username);
//        if(user == null){
//            return false;
//        }
//
//    }
}
