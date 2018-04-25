/**
 * @description: js常量
 */

//-----------------------JSON协议常量start------------------------------
//操作成功与否
var RESULT = "sresult";

//主页加载用户信息
var USERNAME = "username"; //主页加载用户信息

//正常的成功与失败
var RESULT_SUCCESS = 1;
var RESULT_FAIL = 0;

//登录校验返回值
var VALID_SUCCESS = 1; //验证通过
var VALID_FAIL = 0; //验证失败

//注册业务返回值
var REGIST_SUCCESS = 1; //注册成功
var REGIST_USERNAME_EXIST = 2; //用户名已存在
var REGIST_VALIDCODE_FAIL = 3; //验证码错误
var REGIST_FAIL = 4; //注册失败