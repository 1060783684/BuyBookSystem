package org.tzsd.service;

import org.springframework.stereotype.Service;
import org.tzsd.dao.GoodsDAO;
import org.tzsd.dao.UserDAO;
import org.tzsd.pojo.Goods;
import org.tzsd.pojo.User;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

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

    private static ReentrantLock lock = new ReentrantLock();
    private ExecutorService threadPool = Executors.newFixedThreadPool(10); //规定数量的线程池

    /**
     * @description: 关闭线程池
     */
    @PreDestroy
    public void destory(){
        //平缓关闭
        threadPool.shutdown();
    }

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
        List<Goods> results = getGoodsDAO().getGoodsList(type, low, hight, keywords, page * PAGE_SIZE, PAGE_SIZE);
        return results;
    }

    /**
     * @description: 获取对应条件的物品的页数
     * @param type 物品类型
     * @param cost 价格
     * @param keywords 关键字
     * @return
     */
    public long getGoodsPage(int type, int cost, String keywords){
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
        long count = getGoodsDAO().getGoodsListPage(type, low, hight, keywords);
        long pageNum = count/PAGE_SIZE;
        if(count%PAGE_SIZE > 0){
            pageNum += 1;
        }
        return pageNum;
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
        final Goods goods = getGoodsDAO().getGoodsById(goodsId);
        //将这个操作抛出取让一个线程去做,保证商品详情查看的效率
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                if(goods != null) {
                    try {
                        GoodsService.lock.lock();
                        //增加访问量
                        goods.setSell_num(goods.getSell_num() + 1);
                        getGoodsDAO().merge(goods);
                    } finally {
                        if (GoodsService.lock.isHeldByCurrentThread()) {
                            GoodsService.lock.unlock();
                        }
                    }
                }
            }
        });
        return goods;
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
