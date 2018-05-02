package org.tzsd.service;

import org.springframework.stereotype.Service;
import org.tzsd.dao.GoodsDAO;
import org.tzsd.dao.ManagerDAO;
import org.tzsd.dao.ShopCarDAO;
import org.tzsd.dao.StoreDAO;
import org.tzsd.pojo.Goods;
import org.tzsd.pojo.Manager;
import org.tzsd.pojo.Store;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 管理员服务类
 */
@Service("managerService")
public class ManagerService {
    @Resource(name = "managerDao")
    private ManagerDAO managerDAO;

    @Resource(name = "storeDao")
    private StoreDAO storeDAO;

    @Resource(name = "goodsDao")
    private GoodsDAO goodsDAO;

    public ManagerDAO getManagerDAO() {
        return managerDAO;
    }

    public void setManagerDAO(ManagerDAO managerDAO) {
        this.managerDAO = managerDAO;
    }

    public StoreDAO getStoreDAO() {
        return storeDAO;
    }

    public void setStoreDAO(StoreDAO storeDAO) {
        this.storeDAO = storeDAO;
    }

    public GoodsDAO getGoodsDAO() {
        return goodsDAO;
    }

    public void setGoodsDAO(GoodsDAO goodsDAO) {
        this.goodsDAO = goodsDAO;
    }

    private static final int PAGE_SIZE = 9;
//    private static final int PAGE_SIZE2 = 20;

    /**
     * @description: 管理员登陆验证
     * @param username 用户名
     * @param password 密码
     * @return 成功与否
     */
    public boolean validate(String username, String password){
        if(username == null || password == null){
            return false;
        }
        Manager manager = managerDAO.getManagerByName(username);
        if(manager == null){
            return false;
        }
        if(manager.getPassword().equals(password)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * @description: 获取未审核店铺list
     * @return 未审核店铺list
     */
    public List<Store> getNotCheckStoreList(int page){
        return getStoreDAO().getStoreListByCheck(Store.NO, page * PAGE_SIZE, PAGE_SIZE);
    }

    /**
     * @description: 获取未审核物品list
     * @return 未审核物品list
     */
    public List<Goods> getNotCheckGoodsList(int page){
        return getGoodsDAO().getGoodsListByStatus(Goods.STAYUP, page * PAGE_SIZE, PAGE_SIZE);
    }

    /**
     * @description: 返回未审核的商店页面数
     * @return 未审核的商店页面数
     */
    public long getNotCheckStorePage(){
        long count = getStoreDAO().getStoreCountByCheck(Store.NO);
        long page = count/PAGE_SIZE;
        if(count % PAGE_SIZE > 0){
            page += 1;
        }
        return page;
    }

    /**
     * @description: 返回未审核的物品页面数
     * @return 未审核的物品页面数
     */
    public long getNotCheckGoodsPage(){
        long count = getGoodsDAO().getGoodsCountByStatus(Goods.STAYUP);
        long page = count/PAGE_SIZE;
        if(count % PAGE_SIZE > 0){
            page += 1;
        }
        return page;
    }

    /**
     * @description: 审核对应店铺id的店铺
     * @param storeId 店铺id
     * @return 操作结果
     */
    public boolean checkStore(long storeId){
        Store store = getStoreDAO().getStoreById(storeId);
        if(store == null){
            return false;
        }
        if(store.getIsCheck() != Store.NO){
            return false;
        }
        store.setIsCheck(Store.YES);
        getStoreDAO().merge(store);
        return true;
    }

    /**
     * @description: 审核对应店铺id的店铺
     * @param goodsId 物品id
     * @return 操作结果
     */
    public boolean checkGoods(String goodsId){
        Goods goods = getGoodsDAO().getGoodsById(goodsId);
        if(goods == null){
            return false;
        }
        if(goods.getStatus() != Goods.STAYUP){
            return false;
        }
        goods.setStatus(Goods.UP);
        getGoodsDAO().merge(goods);
        return true;
    }
}
