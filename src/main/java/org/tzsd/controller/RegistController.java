package org.tzsd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.pojo.User;
import org.tzsd.service.UserInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 注册业务处理
 */
public class RegistController {

    @Resource(name = "userInfoService")
    private UserInfoService userInfoService;

    public UserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * @description: 处理注册业务
     * @param request
     * @param response
     */
    @RequestMapping("regist.do")
    public void registUser(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String, Object> jsonMap = new HashMap();
        if(username == null || password == null){
            jsonMap.put("result", JSONProtocolConstance.REGIST_FAIL);
        }else {
            User user = userInfoService.getUser(username);
            if(user != null){
                jsonMap.put("result", JSONProtocolConstance.REGIST_USERNAME_EXIST);
            }else{
                try {
                    userInfoService.saveUser(username, password);
                }catch (Exception e){
                    jsonMap.put("result", JSONProtocolConstance.REGIST_FAIL);
                }
            }
        }
    }
}
