package org.tzsd.constance;

/**
 * @description: 业务协议相关的常量
 */
public interface JSONProtocolConstance {
    //登录成功与失败
    int VALID_SUCCESS = 1; //验证通过
    int VALID_FAIL = 0; //验证失败

    //注册业务返回值
    int REGIST_SUCCESS = 1;
    int REGIST_USERNAME_EXIST = 2;
    int REGIST_VALIDCODE_FAIL = 3;
    int REGIST_FAIL = 4;

}
