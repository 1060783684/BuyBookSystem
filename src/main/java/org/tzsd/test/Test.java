package org.tzsd.test;

import org.json.JSONObject;
import org.tzsd.dao.GenericDAO;
import org.tzsd.pojo.AddressInfo;
import org.tzsd.pojo.Store;
import org.tzsd.pojo.User;

import java.util.*;

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
        User user1 = gdao.getById(User.class, 1060783685);
        Store store = gdao.getById(Store.class, 1060783684);
        AddressInfo addressInfo = gdao.getById(AddressInfo.class, "xxx-xxx");
        System.out.println(user);
        System.out.println(store);
        System.out.println(addressInfo);

        System.out.println(user1);
    }

    public static void testJson(){
        Map<String,Object> map = new HashMap();
        map.put("valid",1);

        //带有自动将对象json化的功能
        User user = new User(1060783684,"1060783684","123456");
        map.put("user", user);

        //自带将list对象和数组对象json数组化的功能
        List<User> userList = new ArrayList<>();
        User user1 = new User(1060783684,"1060783684","123456");
        User user2 = new User(1060783685,"1060783685","123455");
        User user3 = new User(1060783686,"1060783686","123456");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        map.put("users", userList);


        JSONObject json = new JSONObject(map);
        System.out.println(json);

    }

    public static void testStringTokenizer(){
        StringTokenizer tokenizer = new StringTokenizer(" xxx  yyy  uuu i", " ");
        System.out.println(tokenizer.countTokens());
        while (tokenizer.hasMoreElements()){
            System.out.println(tokenizer.nextElement());
        }
    }
    public static void main(String[] args){
//        TestSaveDAO();
        TestGetDAO();
//        testJson();
//        testStringTokenizer();
    }
}
