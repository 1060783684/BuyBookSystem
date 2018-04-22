package org.tzsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.tzsd.dao.callback.HibernateCallback;
import org.tzsd.pojo.AddressInfo;

import java.util.List;

/**
 * @description: 操作地址数据的DAO
 */
@Repository("addressInfoDao")
public class AddressInfoDAO extends GenericDAO{

    /**
     * @description: 通过id获取地址信息
     * @param id
     * @return
     */
    public AddressInfo getAddressInfoById(String id){
        return getById(AddressInfo.class, id);
    }

    /**
     * @description: 通过地址id和用户id获取地址信息
     * @param id 地址id
     * @param user_id 用户id
     * @return 地址实例
     */
    public AddressInfo getAddressInfoByIdAndUserId(final String id, final long user_id){
        return getTemplate().doCall(new HibernateCallback<AddressInfo>() {
            @Override
            public AddressInfo doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("getAddressInfoByIdAndUserId");
                query.setParameter("id", id);
                query.setParameter("user_id", user_id);
                return (AddressInfo) query.list().get(0);
            }
        });
    }

    /**
     * @description: 通过userid获取对应的地址list
     * @param userId 用户id
     * @return
     */
    public List<AddressInfo> getAddressInfoListByUserId(final long userId){
        return getTemplate().doCall(new HibernateCallback<List<AddressInfo>>() {
            @Override
            public List<AddressInfo> doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("");
                query.setParameter("user_id", userId);
                return query.list();
            }
        });
    }

    /**
     * @description: 存储地址信息
     * @param addressInfo
     * @return
     */
    public String saveAddressInfo(AddressInfo addressInfo){
        return (String) save(addressInfo);
    }

    /**
     * @description: 通过地址信息id和用户id确认要删除的地址信息
     * @param id 地址id
     * @param user_id 用户id
     * @return
     */
    public Integer deleteAddressInfoByIdAndUserId(final String id, final long user_id){
        return getTemplate().doCall(new HibernateCallback<Integer>() {
            @Override
            public Integer doCall(Session session) throws HibernateException {
                Query query = session.getNamedQuery("deleteAddressInfoByIdAndUserId");
                query.setParameter("id", id);
                query.setParameter("user_id", user_id);
                return query.executeUpdate();
            }
        });
    }

    /**
     * @description: 更新地址信息
     * @param addressInfo
     */
    public void updateAddressInfo(AddressInfo addressInfo){
        merge(addressInfo);
    }
}
