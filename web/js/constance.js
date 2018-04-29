/**
 * @description: js常量
 */

//-----------------------JSON协议常量start------------------------------
//操作成功与否
var RESULT = "sresult";

//主页加载用户信息
var USERNAME = "username"; //主页加载用户信息

//店铺信息获取相关
var STORE_INFO = "storeInfo"; //店铺信息

//正常的成功与失败
var RESULT_SUCCESS = 1;
var RESULT_FAIL = 0;

//登录校验返回值
var VALID_SUCCESS = 1; //验证通过
var VALID_FAIL = 0; //验证失败

//修改密码相关的
var UPDATE_PW_SUCCESS = 1; //修改密码成功
var UPDATE_PW_TOSHORT = 2; //新密码过短
var UPDATE_PW_TOLONG = 3; //新密码过长
var UPDATE_PW_SAME = 4; //新旧密码相同
var UPDATE_PW_FOUL = 5; //密码中含有非法字符
var UPDATE_PW_PWFAIL = 6; //原密码不正确
var UPDATE_PW_FAIL = 0; //操作错误

//注册业务返回值
var REGIST_SUCCESS = 1; //注册成功
var REGIST_USERNAME_EXIST = 2; //用户名已存在
var REGIST_VALIDCODE_FAIL = 3; //验证码错误
var REGIST_FAIL = 4; //注册失败

//上传可能产生的结果
var UPLOAD_SUCCESS = 1; //上传成功
var UPLOAD_TOBIG = 2; //文件超过4M
var UPLOAD_TYPE_ERROR = 3; // 文件类型错误
var UPLOAD_FAIL = 4; //上传错误

//地址相关返回值
var ADDRESS_TOMUCH = 2;

//店铺申请相关
var STORE_APPLY_SUCCESS = 1; //成功
var STORE_APPLY_INFO_NOTFULL = 2; //信息填写不全
var STORE_APPLY_EXIST = 3; //店铺申请已提交
var STORE_APPLY_FAIL = 0; //操作错误