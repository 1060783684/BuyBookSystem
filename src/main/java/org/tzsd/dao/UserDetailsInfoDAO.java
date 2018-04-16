package org.tzsd.dao;

import org.springframework.stereotype.Repository;
import org.tzsd.pojo.UserDetailsInfo;

/**
 * @description: 用户详细信息DAO
 */
@Repository("userDetailsInfoDAO")
public class UserDetailsInfoDAO extends GenericDAO {

    /**
     * @description: 通过id获取用户详细信息
     * @param id　
     * @return 用户详细信息实例
     */
    public UserDetailsInfo getUserDetailsInfoById(long id){
        return getById(UserDetailsInfo.class, id);
    }
}
