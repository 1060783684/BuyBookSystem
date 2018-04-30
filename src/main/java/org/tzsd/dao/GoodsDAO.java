package org.tzsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.Type;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.tzsd.dao.callback.HibernateCallback;
import org.tzsd.pojo.Goods;

import java.util.List;
import java.util.StringTokenizer;

/**
 * @description: 操作数据库中商品数据的一些基本操作
 */
@Repository("goodsDao")
public class GoodsDAO extends GenericDAO{

    /**
     * @description: 根据id获取物品
     * @param id
     * @return
     */
    public Goods getGoodsById(String id){
        return getById(Goods.class, id);
    }

    /**
     * @description: 根据店铺id和物品id获取物品实例
     * @param storeId 店铺id
     * @param id 物品id
     * @return 物品实例
     */
    public Goods getGoodsByStoreIdAndId(final long storeId, final String id){
        return getTemplate().doCall(new HibernateCallback<Goods>() {
            @Override
            public Goods doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getGoodsStoreIdAndId");
                query.setParameter("store_id", storeId);
                query.setParameter("id", id);
                List<Goods> list = query.list();
                Goods goods = null;
                if(list != null && !list.isEmpty()){
                    goods = list.get(0);
                }
                return goods;
            }
        });
    }

    /**
     * @description: 获取对应类型价格对应范围以及对应关键字的物品list
     * @param type 物品类型
     * @param low 最低价格
     * @param hight 最高价格
     * @param keywords 关键字
     * @param start 开始分页号
     * @param size 分页大小
     * @return
     */
    public List<Goods> getGoodsList(final int type, final int low, final int hight, final String keywords, final int start, final int size){
        return getTemplate().doCall(new HibernateCallback<List<Goods>>() {
            @Override
            public List<Goods> doCall(Session session) throws HibernateException {
                StringBuilder hql = new StringBuilder("from Goods");
                String where = null;
                StringBuilder typeEqual = null;
                StringBuilder cost = null;
                StringBuilder keyEqual = null;
                for (int i = 0; i < Goods.TYPES.length; i++) {
                    if (type == Goods.TYPES[i]) {
                        typeEqual = new StringBuilder();
                        where = "where";
                        typeEqual.append(" ").append(where).append(" ");
                        typeEqual.append("type = :type");

                        hql.append(typeEqual);
                        break;
                    }
                }
                if((low >= 0 || hight >= 0)){
                    cost = new StringBuilder();
                    if(where == null){
                        cost.append(" ").append(where).append(" ");
                    }else {
                        cost.append(" ").append("and").append(" ");
                    }

                    if(low <= 0 && hight > 0){
                        cost.append("cost >= ").append(":hight");
                    }else if(low > 0 && hight <= 0){
                        cost.append("cost <= ").append(":low");
                    }else if(low > 0 && hight > 0 && low < hight){
                        cost.append("between ").append(":low").append(" and ").append(":hight");
                    }

                    hql.append(cost);
                }
                if(keywords != null){
                    keyEqual = new StringBuilder();
                    if(where == null){
                        keyEqual.append(" ").append(where).append(" ");
                    }else {
                        keyEqual.append(" ").append("and").append(" ");
                    }

                    keyEqual.append("name = ").append(":name");

                    hql.append(keyEqual);
                }

                Query query = session.createQuery(hql.toString());
                if(typeEqual != null){
                    query.setParameter("type", type);
                }
                if(cost != null){
                    if(low <= 0 && hight > 0){
                        query.setParameter("hight", hight);
                    }else if(low > 0 && hight <= 0){
                        query.setParameter("low", low);
                    }else if(low > 0 && hight > 0){
                        query.setParameter("hight", hight);
                        query.setParameter("low", low);
                    }
                }
                if(keyEqual != null){
                    StringTokenizer tokenizer = new StringTokenizer(keywords," ");
                    StringBuilder name = new StringBuilder();
                    name.append("%");
                    while (tokenizer.hasMoreElements()){
                        name.append(tokenizer.nextElement()).append("%");
                    }
                    query.setParameter("name", name);
                }

                if(start >= 0 && size > 0){
                    query.setFirstResult(start);
                    query.setMaxResults(size);
                }
                return query.list();
            }
        });
    }

    /**
     * @description: 存储物品实例
     * @param goods
     * @return
     */
    public String saveGoods(Goods goods){
        return (String) save(goods);
    }

    /**
     * @description: 修改某个物品的状态信息(上架,带审核,下架)
     * @param id 物品的id
     * @param userId 用户id
     * @param status 想要修改成的状态
     * @return
     */
//    public int updateGoodsStatusByIdAndUserId(final String id, final long userId, final String status){
//        return getTemplate().doCall(new HibernateCallback<Integer>() {
//            @Override
//            public Integer doCall(Session session) throws HibernateException {
//                Query query = session.getNamedQuery("updateGoodsStatusById");
//                query.setParameter("id", id);
//                query.setParameter("user_id", userId);
//                query.setParameter("status", status);
//                return query.executeUpdate();
//            }
//        });
//    }

//    /**
//     * @description: 获取从start开始的size个物品list
//     * @param start 分页开始位置
//     * @param size 分页个数
//     * @return 从start开始的size个物品的list
//     */
//    public List<Goods> getGoodsListLimit(final int start, final int size){
//        return getTemplate().doCall(new HibernateCallback<List<Goods>>() {
//            @Override
//            public List<Goods> doCall(Session session) throws HibernateException {
//                Query query = session.getNamedQuery("getGoodsList");
//                query.setFirstResult(start);
//                query.setMaxResults(size);
//                return query.list();
//            }
//        });
//    }
//
    /**
     * @description: 通过type获取从start开始的size个物品list
     * @param storeId 店铺id
     * @param status 物品状态
     * @param start 分页开始位置
     * @param size 分页个数
     * @return 从start开始的size个物品的list
     */
    public List<Goods> getGoodsListByStoreIdAndStatusLimit(final long storeId,final int status, final int start, final int size){
        return getTemplate().doCall(new HibernateCallback<List<Goods>>() {
            @Override
            public List<Goods> doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getGoodsListByStoreIdAndStatus");
                query.setParameter("store_id", storeId);
                query.setParameter("status", status);
                query.setFirstResult(start);
                query.setMaxResults(size);
                return query.list();
            }
        });
    }

    /**
     * @description: 通过type获取从start开始的size个物品list
     * @param storeId 店铺id
     * @param start 分页开始位置
     * @param size 分页个数
     * @return 从start开始的size个物品的list
     */
    public List<Goods> getGoodsListByStoreIdLimit(final long storeId, final int start, final int size){
        return getTemplate().doCall(new HibernateCallback<List<Goods>>() {
            @Override
            public List<Goods> doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getGoodsListByStoreId");
                query.setParameter("store_id", storeId);
                query.setFirstResult(start);
                query.setMaxResults(size);
                return query.list();
            }
        });
    }

    /**
     * @description: 通过商店id和类型获取符合条件的商品的个数
     * @param storeId 商店id
     * @param status 物品状态
     * @return 符合条件的物品的个数
     */
    public Long getGoodsCountByStoreIdAndStatus(final long storeId,final int status){
        return getTemplate().doCall(new HibernateCallback<Long>() {
            @Override
            public Long doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getGoodsCountByStoreIdAndStatus");
                query.setParameter("store_id", storeId);
                query.setParameter("status", status);
                List<Long> list = query.list();
                long count = 0;
                if(list != null && !list.isEmpty()){
                    count = list.get(0);
                }
                return count;
            }
        });
    }

    /**
     * @description: 通过商店id和类型获取符合条件的商品的个数
     * @param storeId 商店id
     * @return 符合条件的物品的个数
     */
    public Long getGoodsCountByStoreId(final long storeId){
        return getTemplate().doCall(new HibernateCallback<Long>() {
            @Override
            public Long doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getGoodsCountByStoreId");
                query.setParameter("store_id", storeId);
                List<Long> list = query.list();
                long count = 0;
                if(list != null && !list.isEmpty()){
                    count = list.get(0);
                }
                return count;
            }
        });
    }

//
//    /**
//     * @description: 获取价格从low至hight的从start开始的size个物品list
//     * @param low 最低价格
//     * @param higth 最高价格
//     * @param start 分页开始位置
//     * @param size 分页个数
//     * @return 从start开始的size个物品的list
//     */
//    public List<Goods> getGoodsListByCostBetweenLimit(final int low, final int higth, final int start, final int size){
//        return getTemplate().doCall(new HibernateCallback<List<Goods>>() {
//            @Override
//            public List<Goods> doCall(Session session) throws HibernateException {
//                Query query = session.getNamedQuery("getGoodsListByCostBetween");
//                query.setParameter(1,low);
//                query.setParameter(2,higth);
//                query.setFirstResult(start);
//                query.setMaxResults(size);
//                return query.list();
//            }
//        });
//    }
//
//    /**
//     * @description: 获取价格小于low的从start开始的size个物品list
//     * @param low 最高价格
//     * @param start 分页开始位置
//     * @param size 分页个数
//     * @return 从start开始的size个物品的list
//     */
//    public List<Goods> getGoodsListByCostLowLimit(final int low, final int start, final int size){
//        return getTemplate().doCall(new HibernateCallback<List<Goods>>() {
//            @Override
//            public List<Goods> doCall(Session session) throws HibernateException {
//                Query query = session.getNamedQuery("getGoodsListByCostLow");
//                query.setParameter(1,low);
//                query.setFirstResult(start);
//                query.setMaxResults(size);
//                return query.list();
//            }
//        });
//    }
//
//    /**
//     * @description: 获取价格大于hight的从start开始的size个物品list
//     * @param hight 最低价格
//     * @param start 分页开始位置
//     * @param size 分页个数
//     * @return 从start开始的size个物品的list
//     */
//    public List<Goods> getGoodsListByCostHightLimit(final int hight, final int start, final int size){
//        return getTemplate().doCall(new HibernateCallback<List<Goods>>() {
//            @Override
//            public List<Goods> doCall(Session session) throws HibernateException {
//                Query query = session.getNamedQuery("getGoodsListByCostHight");
//                query.setParameter(1,hight);
//                query.setFirstResult(start);
//                query.setMaxResults(size);
//                return query.list();
//            }
//        });
//    }
//
//    /**
//     * @description: 通过type获取价格从low至hight的从start开始的size个物品list
//     * @param type 物品类型
//     * @param low 最低价格
//     * @param higth 最高价格
//     * @param start 分页开始位置
//     * @param size 分页个数
//     * @return 从start开始的size个物品的list
//     */
//    public List<Goods> getGoodsListByTypeAndCostBetweenLimit(final String type, final int low, final int higth, final int start, final int size){
//        return getTemplate().doCall(new HibernateCallback<List<Goods>>() {
//            @Override
//            public List<Goods> doCall(Session session) throws HibernateException {
//                Query query = session.getNamedQuery("getGoodsListByTypeAndCostBetween");
//                query.setParameter(1,type);
//                query.setParameter(2,low);
//                query.setParameter(3,higth);
//                query.setFirstResult(start);
//                query.setMaxResults(size);
//                return query.list();
//            }
//        });
//    }
//
//    /**
//     * @description: 通过type获取价格小于low的从start开始的size个物品list
//     * @param type 物品类型
//     * @param low 最高价格
//     * @param start 分页开始位置
//     * @param size 分页个数
//     * @return 从start开始的size个物品的list
//     */
//    public List<Goods> getGoodsListByTypeAndCostLowLimit(final String type, final int low, final int start, final int size){
//        return getTemplate().doCall(new HibernateCallback<List<Goods>>() {
//            @Override
//            public List<Goods> doCall(Session session) throws HibernateException {
//                Query query = session.getNamedQuery("getGoodsListByTypeAndCostLow");
//                query.setParameter(1,type);
//                query.setParameter(2,low);
//                query.setFirstResult(start);
//                query.setMaxResults(size);
//                return query.list();
//            }
//        });
//    }
//
//    /**
//     * @description: 通过type获取价格大于hight的从start开始的size个物品list
//     * @param type 物品类型
//     * @param hight 最低价格
//     * @param start 分页开始位置
//     * @param size 分页个数
//     * @return 从start开始的size个物品的list
//     */
//    public List<Goods> getGoodsListByTypeAndCostHightLimit(final String type, final int hight, final int start, final int size){
//        return getTemplate().doCall(new HibernateCallback<List<Goods>>() {
//            @Override
//            public List<Goods> doCall(Session session) throws HibernateException {
//                Query query = session.getNamedQuery("getGoodsListByTypeAndCostHight");
//                query.setParameter(1,type);
//                query.setParameter(2,hight);
//                query.setFirstResult(start);
//                query.setMaxResults(size);
//                return query.list();
//            }
//        });
//    }


}
