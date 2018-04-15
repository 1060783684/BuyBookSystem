package org.tzsd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.service.UserInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 处理登录时用户验证请求
 */
@Controller
public class ValidateController extends BaseController {

    @Resource(name = "userInfoService")
    private UserInfoService userInfoService;

    public UserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * @description: 处理登录验证
     * @param request
     * @param response
     */
    @RequestMapping("validate.do")
    public void validateUser(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String, Object> jsonMap = new HashMap();
        if(username == null || password == null){
            jsonMap.put("valid", JSONProtocolConstance.VALID_FAIL);
        }else {
            if(userInfoService.validateUser(username,password)){
                jsonMap.put("valid", JSONProtocolConstance.VALID_SUCCESS);
            }else {
                jsonMap.put("valid", JSONProtocolConstance.VALID_FAIL);
            }
        }

        writeJSONProtocol(response, jsonMap);
    }
}
