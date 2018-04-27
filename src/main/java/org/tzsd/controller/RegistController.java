package org.tzsd.controller;

import org.springframework.stereotype.Controller;
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
@Controller
public class RegistController extends BaseController {

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
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.REGIST_FAIL);
        }else {
            User user = userInfoService.getUser(username);
            if(user != null){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.REGIST_USERNAME_EXIST);
            }else{
                try {
                    long id = userInfoService.regist(username, password);
                    //使用用户名作为账户id
                    if(Long.valueOf(username).equals(id)){
                        jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.REGIST_SUCCESS);
                    }else {
                        jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.REGIST_FAIL);
                    }
                }catch (Exception e){
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.REGIST_FAIL);
                    e.printStackTrace();
                }
            }
        }
        //发送消息
        writeJSONProtocol(response, jsonMap);
    }
}
