package org.tzsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
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
     * @description: 获取对应类型价格对应范围以及对应关键字的物品list
     * @param type 物品类型
     * @param low 最低价格
     * @param hight 最高价格
     * @param keywords 关键字
     * @param start 开始分页号
     * @param size 分页大小
     * @return
     */
    public List<Goods> getGoodsList(final String type, final int low, final int hight, final String keywords, int start, int size){
        return getTemplate().doCall(new HibernateCallback<List<Goods>>() {
            @Override
            public List<Goods> doCall(Session session) throws HibernateException {
                StringBuilder hql = new StringBuilder("from Goods");
                String where = null;
                StringBuilder typeEqual = null;
                StringBuilder cost = null;
                StringBuilder keyEqual = null;
                if(type != null){
                    typeEqual = new StringBuilder();
                    where = "where";
                    typeEqual.append(" ").append(where).append(" ");
                    typeEqual.append("type = :type");

                    hql.append(typeEqual);
                }
                if(low < hight){
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
                    }else if(low > 0 && hight > 0){
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
//    /**
//     * @description: 通过type获取从start开始的size个物品list
//     * @param type 物品类型
//     * @param start 分页开始位置
//     * @param size 分页个数
//     * @return 从start开始的size个物品的list
//     */
//    public List<Goods> getGoodsListByTypeLimit(final String type, final int start, final int size){
//        return getTemplate().doCall(new HibernateCallback<List<Goods>>() {
//            @Override
//            public List<Goods> doCall(Session session) throws HibernateException {
//                Query query = session.getNamedQuery("getGoodsListByType");
//                query.setParameter(1,type);
//                query.setFirstResult(start);
//                query.setMaxResults(size);
//                return query.list();
//            }
//        });
//    }
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