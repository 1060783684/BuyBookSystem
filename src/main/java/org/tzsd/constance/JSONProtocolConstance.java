package org.tzsd.constance;

/**
 * @description: 业务协议相关的常量
 */
public interface JSONProtocolConstance {
    //操作成功与否
    String RESULT = "sresult";

    //------------------------操作成功后的后续的KEY-------------------------
    //主页加载用户信息
    String USERNAME = "username"; //主页加载用户信息

    //用户信息相关
    String USERDETAIINFO = "userDetailInfo"; //用户详细信息 key

    //购物车相关
    String SHOPCAR_LIST_SIZE = "shopCarListSize"; //购物车列表长度 key
    String SHOPCAR_LIST = "shopCarList"; //购物车条目数组 key

    //物品信息相关
    String GOODS_LIST = "goodsList"; //物品list
    String GOODS_INFO = "goodsInfo"; //物品信息

    //订单相关信息
    String ORDER_LIST = "orders"; //订单list
    String ORDER_INFO = "orderInfo"; //订单信息

    //地址信息相关
    String ADDRESS_LIST = "addressList"; //地址list
    String ADDRESS_INFO = "addressInfo"; //地址信息

    //评论相关
    String EVALUATE_LIST = "evaluateList"; //评论list

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
