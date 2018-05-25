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

var REDIRECT = "redirect";

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
var REGIST_UN_CONTAIN_NONNUM = 4; //用户名含有非数字
var REGIST_PW_CONTAIN_ILLCODE = 5; //密码含有非法字符
var REGIST_UP_ISNON = 6; //用户名密码为空
var REGIST_UN_NONPHONE = 7; //用户名不是手机号格式
var REGIST_PW_LENGTH_FAIL = 8; //密码长度不符合要求
var REGIST_FAIL = 0; //注册失败

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
var STORE_APPLY_ILLCODE = 4; //信息中含有非法字符
var STORE_APPLY_FAIL = 0; //操作错误

//发布物品相关
var PUB_SUCCESS = 1; //发布成功
var PUB_UPLOAD_TOBIG = 2; //文件超过4M
var PUB_UPLOAD_TYPE_ERROR = 3; //文件类型错误
var PUB_GOODS_TYPE_ERROR = 4; //发布物品类型错误
var PUB_INFO_ERROR = 5; //信息填写错误
var PUB_IMG_NOTFOUND = 6; //没有文件
var PUB_NON_NUM = 7; //没有填写库存
var PUB_NON_COST = 8; //没有填写价格
var PUB_NON_DESCS = 9; //没有填写描述信息
var PUB_FAIL = 0; //操作错误

//-----------------------JSON协议常量end------------------------------

//物品类型
//类型
var EDUCATION = 0; //教育
var PHILOPHY = 1; //哲学
var ART = 2; //艺术
var CLASSICAL = 3; //古典
var LITERATURE = 4; //文学
var CHILDREN = 5; //少儿
var LAGISLATION = 6; //法律

//宝贝状态
var NOPASS = 3; //审核未通过
var UP = 2; //上架
var STAYUP = 1; //待审核
var DOWN = 0; //下架

//订单状态
var WAIT_PAY = 0; //待支付
var WAIT_PUB = 1; //待发货
var WAIT_INCOME = 2; //待收货
var WAIT_EVAL = 3; //待评价

//重定向相关
var POST_REDIRECT = -1;