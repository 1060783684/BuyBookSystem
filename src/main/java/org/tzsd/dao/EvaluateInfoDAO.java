package org.tzsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.tzsd.dao.callback.HibernateCallback;
import org.tzsd.pojo.EvaluateInfo;

import java.util.List;

/**
 * @description: 评论相关数据的DAO操作
 */
@Repository("evaluateInfoDao")
public class EvaluateInfoDAO extends GenericDAO{

    /**
     * @description: 通过评价id获取订单实例
     * @param evaluateId　评价id
     * @return 评价实例
     */
    public EvaluateInfo getEvaluateInfoById(String evaluateId){
        return getById(EvaluateInfo.class, evaluateId);
    }

    /**
     * @description: 通过物品id获取物品list
     * @param id 物品id
     * @return 物品list
     */
    public List<EvaluateInfo> getEvaluateListByGoodsId(String id){
        return getTemplate().doCall(new HibernateCallback<List<EvaluateInfo>>() {
            @Override
            public List<EvaluateInfo> doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getEvaluateInfoListByGoodsId");
                query.setParameter("id", id);
                return query.list();
            }
        });
    }

    /**
     * @description: 删除对应用户id和评价id的评价
     * @param id 评价id
     * @param userId 用户id
     * @return
     */
    public Integer deleteEvaluateInfo(String id, long userId){
        return getTemplate().doCall(new HibernateCallback<Integer>() {
            @Override
            public Integer doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("deleteEvaluateInfoByIdAndUserId");
                query.setParameter("id", id);
                query.setParameter("user_id", userId);
                return query.executeUpdate();
            }
        });
    }

    /**
     * @description: 保存评价信息
     * @param evaluateInfo　评价实例
     * @return 保存成功的评价id
     */
    public String saveEvaluateInfo(EvaluateInfo evaluateInfo){
        return (String) save(evaluateInfo);
    }
}
