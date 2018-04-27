package org.tzsd.service;

import org.springframework.stereotype.Service;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.dao.AddressInfoDAO;
import org.tzsd.dao.UserDAO;
import org.tzsd.pojo.AddressInfo;
import org.tzsd.pojo.User;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @description: 地址信息相关的服务类
 */
@Service("addressInfoService")
public class AddressInfoService {
    @Resource(name = "addressInfoDao")
    private AddressInfoDAO addressInfoDAO;

    @Resource(name = "userDao")
    private UserDAO userDAO;

    public AddressInfoDAO getAddressInfoDAO() {
        return addressInfoDAO;
    }

    public void setAddressInfoDAO(AddressInfoDAO addressInfoDAO) {
        this.addressInfoDAO = addressInfoDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * @description: 添加地址信息进入数据库
     * @param address 详细地址
     * @param name 收件人名字
     * @param mail 邮编
     * @param phone 电话号
     * @param username 用于获取用户信息的用户名
     * @return 成功与否
     */
    public int addAddressInfo(String address, String name, String mail, String phone, String username) {
        User user = getUserDAO().getUserByName(username);
        if (user == null) {
            return JSONProtocolConstance.REGIST_FAIL;
        }
        List<AddressInfo> list = getAddressInfoDAO().getAddressInfoListByUserId(user.getId());
        if(list.size() >= 8){
            return JSONProtocolConstance.ADDRESS_TOMUCH;
        }
        UUID uuid = null;
        do {
            uuid = UUID.randomUUID();
            System.out.println(uuid);
        } while (uuid == null || getAddressInfoDAO().getAddressInfoById(uuid.toString()) != null);
        AddressInfo addressInfo = new AddressInfo(uuid.toString(), name, mail, user.getId(), address, phone);
        try {
            String addressId = getAddressInfoDAO().saveAddressInfo(addressInfo);
            if(addressId == null || !addressId.equals(uuid.toString())){
                return JSONProtocolConstance.REGIST_FAIL;
            }
        }catch (Exception e){
            e.printStackTrace();
            return JSONProtocolConstance.REGIST_FAIL;
        }
        return JSONProtocolConstance.REGIST_SUCCESS;
    }

    /**
     * @description: 通过用户名获取用户的地址list
     * @param username 用户名
     * @return 地址list
     */
    public List<AddressInfo> getAddressInfoListByUserId(String username){
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return null;
        }
        List<AddressInfo> list = null;
        try{
            list = getAddressInfoDAO().getAddressInfoListByUserId(user.getId());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return list;
    }

    /**
     * @description: 通过用户名和地址id唯一确认一个地址信息
     * @param username 用户名
     * @param addressId 地址id
     * @return 地址信息实例
     */
    public AddressInfo getAddressInfo(String username, String addressId){
        if(username == null || addressId == null){
            return null;
        }
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return null;
        }
        AddressInfo addressInfo = null;
        try {
            addressInfo = getAddressInfoDAO().getAddressInfoByIdAndUserId(addressId, user.getId());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return addressInfo;
    }

    /**
     * @description: 根据用户名和地址id删除地址记录
     * @param username
     * @param addressId
     * @return
     */
    public boolean deleteAddressInfo(String username, String addressId){
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return false;
        }
        int result = getAddressInfoDAO().deleteAddressInfoByIdAndUserId(addressId, user.getId());
        if(result <= 0){
            return false;
        }
        return true;
    }

    //4. 更新

    /**
     * @description: 地址信息更新操作
     * @param username 用户名
     * @param addressId 地址信息id
     * @param address 更改后的地址信息
     * @param name 更改后的收件人姓名
     * @param mail 更改后的邮编
     * @param phone 更改后的手机号
     * @return
     */
    public boolean updateAddressInfo(String username, String addressId, String address, String name, String mail, String phone){
        User user = getUserDAO().getUserByName(username);
        if(user == null){
            return false;
        }
        AddressInfo addressInfo = getAddressInfoDAO().getAddressInfoByIdAndUserId(addressId, user.getId());
        if(addressId == null){
            return false;
        }
        if(address != null) {
            addressInfo.setAddr(address);
        }
        if(name != null) {
            addressInfo.setName(name);
        }
        if(mail != null) {
            addressInfo.setMail(mail);
        }
        if(phone != null) {
            addressInfo.setPhone(phone);
        }
        try {
            getAddressInfoDAO().updateAddressInfo(addressInfo);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
