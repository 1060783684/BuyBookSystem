package org.tzsd.constance;

/**
 * @description: 业务协议相关的常量
 */
public interface JSONProtocolConstance {
    //操作成功与否
    String RESULT = "result";

    //------------------------操作成功后的后续的KEY-------------------------
    //用户信息相关
    String USERDETAIINFO = "userDetailInfo"; //用户详细信息 key

    //购物车相关
    String SHOPCAR_LIST_SIZE = "shopCarListSize"; //购物车列表长度 key
    String SHOPCAR_LIST = "shopCarList"; //购物车条目数组 key

    //物品信息相关
    String GOODS_LIST = "goods";

    //------------------------登录成功与失败-------------------------------
    int VALID_SUCCESS = 1; //验证通过
    int VALID_FAIL = 0; //验证失败

    //注册业务返回值
    int REGIST_SUCCESS = 1;
    int REGIST_USERNAME_EXIST = 2;
    int REGIST_VALIDCODE_FAIL = 3;
    int REGIST_FAIL = 4;

    //正常的成功与失败
    int RESULT_SUCCESS = 1;
    int RESULT_FAIL = 0;
}
