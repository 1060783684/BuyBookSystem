package org.tzsd.controller.user;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.controller.BaseController;
import org.tzsd.manager.LoginUserManager;
import org.tzsd.pojo.*;
import org.tzsd.service.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 处理登录后的用户请求的Controller
 */
@Controller
@RequestMapping("/user")
public class LoginUserController extends BaseController {

    @Resource(name = "userInfoService")
    private UserInfoService userInfoService;

    @Resource(name = "orderService")
    private OrderService orderService;

    @Resource(name = "addressInfoService")
    private AddressInfoService addressInfoService;

    @Resource(name = "evaluateInfoService")
    private EvaluateInfoService evaluateInfoService;

    @Resource(name = "storeService")
    private StoreService storeService;

    @Resource(name = "buyGoodsService")
    private BuyGoodsService buyGoodsService;

    public UserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public AddressInfoService getAddressInfoService() {
        return addressInfoService;
    }

    public void setAddressInfoService(AddressInfoService addressInfoService) {
        this.addressInfoService = addressInfoService;
    }

    public EvaluateInfoService getEvaluateInfoService() {
        return evaluateInfoService;
    }

    public void setEvaluateInfoService(EvaluateInfoService evaluateInfoService) {
        this.evaluateInfoService = evaluateInfoService;
    }

    public StoreService getStoreService() {
        return storeService;
    }

    public void setStoreService(StoreService storeService) {
        this.storeService = storeService;
    }


    public BuyGoodsService getBuyGoodsService() {
        return buyGoodsService;
    }

    public void setBuyGoodsService(BuyGoodsService buyGoodsService) {
        this.buyGoodsService = buyGoodsService;
    }

    /**

     * @param request
     * @param response
     * @description: 用户注销登陆, session无效化, 删除session管理者中的session
     */
    @RequestMapping("/logout.do")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = request.getSession().getId();
        System.out.println(sessionId + " logout");
        if (LoginUserManager.getInstance().getUsers().containsKey(sessionId)) {
            request.getSession().invalidate();
            LoginUserManager.getInstance().invaldateSession(sessionId);
        }
        Map<String, Object> jsonMap = new HashMap();
        jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 修改密码
     * @param request
     * @param response
     */
    @RequestMapping("/updatePassword.do")
    public void updatePassword(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);
        if (user == null) {
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        } else {
            String username = user.getName();
            String newPassword = request.getParameter("newPassword");
            String password = request.getParameter("password");

            if (username == null || newPassword == null || password == null) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            } else {
                int result = getUserInfoService().updatePassword(username, password, newPassword);
                jsonMap.put(JSONProtocolConstance.RESULT, result);
            }
        }

        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @param request
     * @param response
     * @description: 获取用户详细信息
     */
    @RequestMapping("/getUserDetailInfo.do")
    public void getUserDetailInfo(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);
        if (user == null) {
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        } else {
            String username = user.getName();
            if (username == null) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            } else {
                //操作
                UserDetailsInfo userDetailsInfo = getUserInfoService().searchUserDetailsInfo(username);
                if (userDetailsInfo == null) {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                } else {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                    jsonMap.put(JSONProtocolConstance.USERDETAIINFO, userDetailsInfo);
                }
            }
        }

        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @param request
     * @param response
     * @description: 修改用户详细信息
     */
    @RequestMapping("/updateUserInfo.do")
    public void updateDetailInfo(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);
        if (user == null) {
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        } else {
            long id = user.getId();
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            int sex = -1;
            try {
                sex = Integer.valueOf(request.getParameter("sex"));
            } catch (Exception e) {

            }
            try {
                getUserInfoService().updateUserDetailsInfo(id, name, phone, sex);
            } catch (Exception e) {

            }
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @param request
     * @param response
     * @description: 更新用户头像信息
     */
    @RequestMapping("/updateUserHead.do")
    public void updateUserHead(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);
        if (user == null) {
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.UPLOAD_FAIL);
        } else {
            if (ServletFileUpload.isMultipartContent(request)) {
                long id = user.getId();
                DiskFileItemFactory fac = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(fac);
                upload.setHeaderEncoding("utf-8");
                List fileList = null;
                try {
                    //从request中获取文件列表
                    fileList = upload.parseRequest(request);
                    int result = getUserInfoService().updateUserHeadInfo(id, fileList);
                    //常规方式获取结果
                    jsonMap.put(JSONProtocolConstance.RESULT, result);
                } catch (FileUploadException e) {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.UPLOAD_FAIL);
                    e.printStackTrace();
                }
            } else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.UPLOAD_FAIL);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 欲购买宝贝
     * @param request
     * @param response
     */
    @RequestMapping("/readyBuyGoods.do")
    public void readyBuyGoods(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);
        String goodsId = request.getParameter("goodsId");
        String addressId = request.getParameter("addressId");
        String numStr = request.getParameter("num");
        if(user == null || goodsId == null || numStr == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.READY_BUY_FAIL);
        }else {
            try{
                long num = Long.valueOf(numStr);
                Object[] results = getBuyGoodsService().readyBuyGoods(user.getName(), addressId, goodsId, num);
                int result = (int)results[0];
                jsonMap.put(JSONProtocolConstance.RESULT, result);
                if(result == JSONProtocolConstance.READY_BUY_SUCCESS){
                    String orderId = (String)results[1];
                    jsonMap.put(JSONProtocolConstance.ORDER_ID, orderId);
                }
            }catch (Exception e){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.READY_BUY_FAIL);
                e.printStackTrace();
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 购买宝贝
     * @param request
     * @param response
     */
    @RequestMapping("/buyGoods.do")
    public void buyGoods(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);
        String orderId = request.getParameter("orderId");
        if (user == null || orderId == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            if(getBuyGoodsService().buyGoods(user.getName(), orderId)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
            }else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 确认收货
     * @param request
     * @param response
     */
    @RequestMapping("/incomeGoods.do")
    public void incomeGoods(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);
        String orderId = request.getParameter("orderId");
        if (user == null || orderId == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            if(getBuyGoodsService().incomeGoods(user.getName(), orderId)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
            }else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }
    /**
     * @param request
     * @param response
     * @description: 获取订单list
     */
    @RequestMapping("/getOrderList.do")
    public void getOrderList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);
        String statusStr = request.getParameter("status");
        String pageStr = request.getParameter("page");
        int page = 0;
        try {
            page = Integer.valueOf(pageStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        String username = user.getName();
        if (user == null || statusStr == null) {
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        } else {
            try {
                int status = Integer.valueOf(statusStr);
                //操作
                List orders = getOrderService().getOrderList(username, status, page);
                long pageNum = getOrderService().getOrderListPage(username, status);
                if (orders == null || orders.isEmpty()) {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                } else {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                    jsonMap.put(JSONProtocolConstance.ORDER_LIST, orders);
                    jsonMap.put(JSONProtocolConstance.PAGE, pageNum);
                }
            }catch (Exception e){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                e.printStackTrace();
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @param request
     * @param response
     * @description: 获取订单信息
     */
    @RequestMapping("/getOrderInfo")
    public void getOrderInfo(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String orderId = request.getParameter("orderId");
        Map<String, Object> jsonMap = new HashMap();
        if (username == null || orderId == null) {
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        } else {
            //判断用户名和session是否对应
            String sessionId = request.getSession().getAttribute("sessionId").toString();
            if (!SessionService.getInstance().isUser(sessionId, username)) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                writeJSONProtocol(response, jsonMap);
                return;
            }
            //操作
            Order order = getOrderService().getOrderInfo(orderId, username);
            if (order == null) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            } else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                jsonMap.put(JSONProtocolConstance.ORDER_INFO, order);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @param request
     * @param response
     * @description: 获取地址信息
     */
    @RequestMapping("/getAddressInfo.do")
    public void getAddressInfo(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);

        if(user == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            String username = user.getName();
            String addressId = request.getParameter("addressId");

            if (username == null || addressId == null) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            } else {
                //操作
                AddressInfo addressInfo = getAddressInfoService().getAddressInfo(username, addressId);
                if (addressInfo == null) {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                } else {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                    jsonMap.put(JSONProtocolConstance.ADDRESS_INFO, addressInfo);
                }
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @param request
     * @param response
     * @description: 获取一个用户的所有地址简要信息
     */
    @RequestMapping("/getAddressList.do")
    public void getAdderssInfoList(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);

        if(user == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            String username = user.getName();

            if (username == null) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            } else {
                //操作
                List<AddressInfo> list = getAddressInfoService().getAddressInfoListByUserId(username);
                if (list == null || list.isEmpty()) {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                } else {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                    jsonMap.put(JSONProtocolConstance.ADDRESS_LIST, list);
                }
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @param request
     * @param response
     * @description: 删除一个用户的某个特定的地址信息
     */
    @RequestMapping("/deleteAddressInfo.do")
    public void deleteAddressInfo(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);

        if(user == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            String username = user.getName();
            String addressId = request.getParameter("addressId");

            if (username == null || addressId == null) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            } else {
                //操作
                if (getAddressInfoService().deleteAddressInfo(username, addressId)) {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                } else {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                }
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @param request
     * @param response
     * @description: 添加地址信息
     */
    @RequestMapping("/addAddressInfo.do")
    public void addAddressInfo(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);

        if(user == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            String address = request.getParameter("address");
            String name = request.getParameter("name");
            String mail = request.getParameter("mail");
            String phone = request.getParameter("phone");
            String username = user.getName();

            if (address == null || name == null || mail == null || phone == null || username == null) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            } else {
                //操作结果状态
                int result = getAddressInfoService().addAddressInfo(address, name, mail, phone, username);
                jsonMap.put(JSONProtocolConstance.RESULT, result);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @param request
     * @param response
     * @description: 更新某个特定的地址信息
     */
    @RequestMapping("/updateAddressInfo.do")
    public void updateAddressInfo(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);

        if(user == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            String addressId = request.getParameter("addressId");
            String address = request.getParameter("address");
            String name = request.getParameter("name");
            String mail = request.getParameter("mail");
            String phone = request.getParameter("phone");
            String username = user.getName();

            if (username == null || addressId == null) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            } else {
                //操作
                if (getAddressInfoService().updateAddressInfo(username, addressId, address, name, mail, phone)) {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                } else {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                }
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @param request
     * @param response
     * @description: 删除某个用户对某个商品的评论
     */
    @RequestMapping("/deleteEvaluate")
    public void deleteEvaluate(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String evaluateId = request.getParameter("evaluateId");
        Map<String, Object> jsonMap = new HashMap();
        if (username == null || evaluateId == null) {
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        } else {
            //判断用户名和session是否对应
            String sessionId = request.getSession().getAttribute("sessionId").toString();
            if (!SessionService.getInstance().isUser(sessionId, username)) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                writeJSONProtocol(response, jsonMap);
                return;
            }
            //操作
            if (getEvaluateInfoService().deleteEvaluate(username, evaluateId)) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
            } else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 申请商店操作
     * @param request
     * @param response
     */
    @RequestMapping("/applyStore.do")
    public void applyStore(HttpServletRequest request, HttpServletResponse response)  {
        Map<String, Object> jsonMap = new HashMap();
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);

        if(user == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.STORE_APPLY_FAIL);
        }else {
            String name = request.getParameter("name"); //公司法人名字
            String idNumber = request.getParameter("idnumber"); //公司法人身份证id
            String storeName = request.getParameter("storeName"); //商店名称
            String type = request.getParameter("type"); //营业类型
            String username = user.getName(); //用户名

            if (username == null) {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.STORE_APPLY_FAIL);
            } else {
                //操作
                int result = getStoreService().storeApply(username, name, idNumber, storeName, type);
                jsonMap.put(JSONProtocolConstance.RESULT, result);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }
}
