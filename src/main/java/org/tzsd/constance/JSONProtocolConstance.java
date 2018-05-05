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
    String ORDER_ID = "orderId"; //订单id

    //地址信息相关
    String ADDRESS_LIST = "addressList"; //地址list
    String ADDRESS_INFO = "addressInfo"; //地址信息

    //评论相关
    String EVALUATE_LIST = "evaluateList"; //评论list

    //店铺信息获取相关
    String STORE_INFO = "storeInfo"; //店铺信息
    String STORE_LIST = "storeList"; //商店list

    //店铺申请人信息相关
    String STOREUSER_LIST = "storeUserList"; //店铺申请人list

    String PAGE = "page"; //业务无关,代表页数

    //------------------------post重定向---------------------------------
    String REDIRECT = "redirect";

    //------------------------登录成功与失败-------------------------------
    int VALID_SUCCESS = 1; //验证通过
    int VALID_FAIL = 0; //验证失败

    //修改密码相关的
    int UPDATE_PW_SUCCESS = 1; //修改密码成功
    int UPDATE_PW_TOSHORT = 2; //新密码过短
    int UPDATE_PW_TOLONG = 3; //新密码过长
    int UPDATE_PW_SAME = 4; //新旧密码相同
    int UPDATE_PW_FOUL = 5; //密码中含有非法字符
    int UPDATE_PW_PWFAIL = 6; //原密码不正确
    int UPDATE_PW_FAIL = 0; //操作错误

    //注册业务返回值
    int REGIST_SUCCESS = 1;
    int REGIST_USERNAME_EXIST = 2;
    int REGIST_VALIDCODE_FAIL = 3; //验证码错误
    int REGIST_UN_CONTAIN_NONNUM = 4; //用户名含有非数字
    int REGIST_PW_CONTAIN_ILLCODE = 5; //密码含有非法字符
    int REGIST_UP_ISNON = 6; //用户名密码为空
    int REGIST_UN_NONPHONE = 7; //用户名不是手机号格式
    int REGIST_PW_LENGTH_FAIL = 8; //密码长度不符合要求
    int REGIST_FAIL = 0;

    //正常的成功与失败
    int RESULT_SUCCESS = 1;
    int RESULT_FAIL = 0;

    //上传可能产生的结果
    int UPLOAD_SUCCESS = 1; //上传成功
    int UPLOAD_TOBIG = 2; //文件超过4M
    int UPLOAD_TYPE_ERROR = 3; // 文件类型错误
    int UPLOAD_FAIL = 4; //上传错误

    //地址相关返回值
    int ADDRESS_TOMUCH = 2;

    //店铺申请相关
    int STORE_APPLY_SUCCESS = 1; //成功
    int STORE_APPLY_INFO_NOTFULL = 2; //信息填写不全
    int STORE_APPLY_EXIST = 3; //店铺申请已提交
    int STORE_APPLY_FAIL = 0; //操作错误

    //发布物品相关
    int PUB_SUCCESS = 1; //发布成功
    int PUB_UPLOAD_TOBIG = 2; //文件超过4M
    int PUB_UPLOAD_TYPE_ERROR = 3; //文件类型错误
    int PUB_GOODS_TYPE_ERROR = 4; //发布物品类型错误
    int PUB_INFO_ERROR = 5; //信息填写错误
    int PUB_IMG_NOTFOUND = 6; //没有文件
    int PUB_FAIL = 0; //操作错误

    //预购买相关
    int READY_BUY_SUCCESS = 1; //操作成功
    int READY_BUY_NUM_NOT_ENG = 2; //库存不足
    int READY_BUY_FAIL = 3; //操作失败

    //重定向相关
    int POST_REDIRECT = -1;
}
