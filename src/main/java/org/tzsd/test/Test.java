package org.tzsd.test;

import org.json.JSONObject;
import org.tzsd.dao.GenericDAO;
import org.tzsd.pojo.AddressInfo;
import org.tzsd.pojo.Store;
import org.tzsd.pojo.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 测试类
 */
public class Test {

    /**
     * @description: 测试DAO
     */
    public static void TestSaveDAO(){
        GenericDAO gdao = new GenericDAO();
        User user = new User(1060783684,"1060783684","19961029dwe");
        gdao.save(user);
        Store store = new Store(1060783684,"xxx","god store","xxx/xxx/xxx","haikou",
                1060783684, 500, Store.Check.YES);
        gdao.save(store);
        AddressInfo addressInfo = new AddressInfo("xxx-xxx","wangwei","xxx@qq.com", 1060783684, "haikou");
        gdao.save(addressInfo);
    }

    public static void TestGetDAO(){
        GenericDAO gdao = new GenericDAO();
        User user = gdao.getById(User.class, 1060783684);
        Store store = gdao.getById(Store.class, 1060783684);
        AddressInfo addressInfo = gdao.getById(AddressInfo.class, "xxx-xxx");
        System.out.println(user);
        System.out.println(store);
        System.out.println(addressInfo);
    }

    public static void testJson(){
        Map<String,Object> map = new HashMap();
        map.put("valid",1);
        JSONObject json = new JSONObject(map);
        System.out.println(json);
    }
    public static void main(String[] args){
//        TestSaveDAO();
//        TestGetDAO();
        testJson();
    }
}
