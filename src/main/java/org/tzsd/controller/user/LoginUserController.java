package org.tzsd.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.controller.BaseController;
import org.tzsd.pojo.AddressInfo;
import org.tzsd.pojo.Order;
import org.tzsd.pojo.UserDetailsInfo;
import org.tzsd.service.AddressInfoService;
import org.tzsd.service.OrderService;
import org.tzsd.service.UserInfoService;

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

    @RequestMapping("/getAddressInfo")
    public void getAddressInfo(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String addressId = request.getParameter("addressId");
        Map<String, Object> jsonMap = new HashMap();
        if(username == null || addressId == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
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
}
