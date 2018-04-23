package org.tzsd.service;

import org.springframework.stereotype.Service;
import org.tzsd.dao.EvaluateInfoDAO;
import org.tzsd.dao.UserDAO;
import org.tzsd.pojo.EvaluateInfo;
import org.tzsd.pojo.User;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 评价相关的服务类
 */
@Service("evaluateInfoService")
public class EvaluateInfoService {

    @Resource(name = "userDao")
    private UserDAO userDAO;

    @Resource(name = "evaluateInfoDao")
    private EvaluateInfoDAO evaluateInfoDAO;

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public EvaluateInfoDAO getEvaluateInfoDAO() {
        return evaluateInfoDAO;
    }

    public void setEvaluateInfoDAO(EvaluateInfoDAO evaluateInfoDAO) {
        this.evaluateInfoDAO = evaluateInfoDAO;
    }

    //1.添加评论

    //2.删除评论
    /**
     * @description: 删除用户的评论操作
     * @param username 用户名
     * @param evaluateId　评论id
     * @return
     */
    public boolean deleteEvaluate(String username, String evaluateId){
        if(username == null || evaluateId == null){
            return false;
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return false;
        }
        int count = getEvaluateInfoDAO().deleteEvaluateInfo(evaluateId, user.getId());
        if(count <= 0){
            return false;
        }
        return true;
    }

    //3.获取评论
    /**
     * @description: 获取某个物品的所有评论
     * @param goodId 物品id
     * @return
     */
    public List<EvaluateInfo> getEvaluateList(String goodId){
        if(goodId == null){
            return null;
        }
        List<EvaluateInfo> list = getEvaluateInfoDAO().getEvaluateListByGoodsId(goodId);
        return list;
    }
}
