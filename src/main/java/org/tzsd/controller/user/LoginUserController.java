package org.tzsd.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.controller.BaseController;
import org.tzsd.manager.LoginUserManager;
import org.tzsd.pojo.AddressInfo;
import org.tzsd.pojo.EvaluateInfo;
import org.tzsd.pojo.Order;
import org.tzsd.pojo.UserDetailsInfo;
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

    /**
     * @description: 用户注销登陆,session无效化,删除session管理者中的session
     * @param request
     * @param response
     */
    @RequestMapping("/logout.do")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        String sessionId = request.getSession().getId();
        System.out.println(sessionId+" logout");
        if(LoginUserManager.getInstance().getUsers().containsKey(sessionId)){
            request.getSession().invalidate();
            LoginUserManager.getInstance().invaldateSession(sessionId);
        }
        Map<String, Object> jsonMap = new HashMap();
        jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 获取用户详细信息
     * @param request
     * @param response
     */
    @RequestMapping("/getUserDetailInfo")
    public void getUserDetailInfo(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        Map<String, Object> jsonMap = new HashMap();
        if(username == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }
        //判断用户名和session是否对应
        String sessionId = request.getSession().getAttribute("sessionId").toString();
        if(!SessionService.getInstance().isUser(sessionId, username)){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            writeJSONProtocol(response, jsonMap);
            return;
        }
        //操作
        UserDetailsInfo userDetailsInfo = getUserInfoService().searchUserDetailsInfo(username);
        if(userDetailsInfo == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }
        jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
        jsonMap.put(JSONProtocolConstance.USERDETAIINFO, userDetailsInfo);

        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 获取订单list
     * @param request
     * @param response
     */
    @RequestMapping("/getOrderList")
    public void getOrderList(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String status = request.getParameter("status");
        Map<String, Object> jsonMap = new HashMap();
        if(username == null || status == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            //判断用户名和session是否对应
            String sessionId = request.getSession().getAttribute("sessionId").toString();
            if(!SessionService.getInstance().isUser(sessionId, username)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                writeJSONProtocol(response, jsonMap);
                return;
            }
            //操作
            List<Order> orders = getOrderService().getOrderList(username, status);
            if(orders == null || orders.isEmpty()){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            }else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                jsonMap.put(JSONProtocolConstance.ORDER_LIST, orders);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 获取订单信息
     * @param request
     * @param response
     */
    @RequestMapping("/getOrderInfo")
    public void getOrderInfo(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String orderId = request.getParameter("orderId");
        Map<String, Object> jsonMap = new HashMap();
        if(username == null || orderId == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            //判断用户名和session是否对应
            String sessionId = request.getSession().getAttribute("sessionId").toString();
            if(!SessionService.getInstance().isUser(sessionId, username)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                writeJSONProtocol(response, jsonMap);
                return;
            }
            //操作
            Order order = getOrderService().getOrderInfo(orderId, username);
            if(order == null){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            }else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                jsonMap.put(JSONProtocolConstance.ORDER_INFO, order);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 获取地址信息
     * @param request
     * @param response
     */
    @RequestMapping("/getAddressInfo")
    public void getAddressInfo(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String addressId = request.getParameter("addressId");
        Map<String, Object> jsonMap = new HashMap();
        if(username == null || addressId == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            //判断用户名和session是否对应
            String sessionId = request.getSession().getAttribute("sessionId").toString();
            if(!SessionService.getInstance().isUser(sessionId, username)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                writeJSONProtocol(response, jsonMap);
                return;
            }
            //操作
            AddressInfo addressInfo = getAddressInfoService().getAddressInfo(username, addressId);
            if(addressInfo == null){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            }else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                jsonMap.put(JSONProtocolConstance.ADDRESS_INFO, addressInfo);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 获取一个用户的所有地址简要信息
     * @param request
     * @param response
     */
    @RequestMapping("/getAddressList")
    public void getAdderssInfoList(HttpServletRequest request, HttpServletResponse response){
        String username =  request.getParameter("username");
        Map<String, Object> jsonMap = new HashMap();
        if(username == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            //判断用户名和session是否对应
            String sessionId = request.getSession().getAttribute("sessionId").toString();
            if(!SessionService.getInstance().isUser(sessionId, username)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                writeJSONProtocol(response, jsonMap);
                return;
            }
            //操作
            List<AddressInfo> list = getAddressInfoService().getAddressInfoListByUserId(username);
            if(list == null || list.isEmpty()){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            }else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
                jsonMap.put(JSONProtocolConstance.ADDRESS_LIST, list);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 删除一个用户的某个特定的地址信息
     * @param request
     * @param response
     */
    @RequestMapping("/deleteAddressInfo")
    public void deleteAddressInfo(HttpServletRequest request, HttpServletResponse response){
        String username =  request.getParameter("username");
        String addressId = request.getParameter("addressId");
        Map<String, Object> jsonMap = new HashMap();
        if(username == null || addressId == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            //判断用户名和session是否对应
            String sessionId = request.getSession().getAttribute("sessionId").toString();
            if(!SessionService.getInstance().isUser(sessionId, username)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                writeJSONProtocol(response, jsonMap);
                return;
            }
            //操作
            if(getAddressInfoService().deleteAddressInfo(username, addressId)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
            }else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 添加地址信息
     * @param request
     * @param response
     */
    @RequestMapping("/addAddressInfo")
    public void addAddressInfo(HttpServletRequest request, HttpServletResponse response){
        String address = request.getParameter("address");
        String name = request.getParameter("name");
        String mail = request.getParameter("mail");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        Map<String, Object> jsonMap = new HashMap();
        if(address == null || name == null || mail == null || phone == null || username == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            //判断用户名和session是否对应
            String sessionId = request.getSession().getAttribute("sessionId").toString();
            if(!SessionService.getInstance().isUser(sessionId, username)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                writeJSONProtocol(response, jsonMap);
                return;
            }
            //操作
            if(getAddressInfoService().addAddressInfo(address, name, mail, phone, username)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
            }else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 更新某个特定的地址信息
     * @param request
     * @param response
     */
    @RequestMapping("/updateAddressInfo")
    public void updateAddressInfo(HttpServletRequest request, HttpServletResponse response){
        String addressId = request.getParameter("addressId");
        String address = request.getParameter("address");
        String name = request.getParameter("name");
        String mail = request.getParameter("mail");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        Map<String, Object> jsonMap = new HashMap();
        if(username == null || addressId == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            //判断用户名和session是否对应
            String sessionId = request.getSession().getAttribute("sessionId").toString();
            if(!SessionService.getInstance().isUser(sessionId, username)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                writeJSONProtocol(response, jsonMap);
                return;
            }
            //操作
            if (getAddressInfoService().updateAddressInfo(username, addressId, address, name, mail, phone)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
            }else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }

    /**
     * @description: 删除某个用户对某个商品的评论
     * @param request
     * @param response
     */
    @RequestMapping("/deleteEvaluate")
    public void deleteEvaluate(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String evaluateId = request.getParameter("evaluateId");
        Map<String, Object> jsonMap = new HashMap();
        if(username == null || evaluateId == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            //判断用户名和session是否对应
            String sessionId = request.getSession().getAttribute("sessionId").toString();
            if(!SessionService.getInstance().isUser(sessionId, username)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
                writeJSONProtocol(response, jsonMap);
                return;
            }
            //操作
            if(getEvaluateInfoService().deleteEvaluate(username, evaluateId)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
            }else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
            }
        }
        writeJSONProtocol(response, jsonMap);
    }
}
