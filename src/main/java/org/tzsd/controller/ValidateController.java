package org.tzsd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.manager.LoginUserManager;
import org.tzsd.manager.session.SessionManager;
import org.tzsd.pojo.User;
import org.tzsd.service.UserInfoService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
     * @description: 初始化session管理器
     */
    @PostConstruct
    public void init(){
        SessionManager.getInstance().init();
    }

    /**
     * @description: 销毁session管理器
     */
    @PreDestroy
    public void destory(){
        SessionManager.getInstance().destory();
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
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.VALID_FAIL);
        }else {
            User user = null;
            if((user = userInfoService.validateUser(username,password)) != null){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.VALID_SUCCESS);
                //设置Cookie
                HttpSession session = request.getSession(true);
                session.setMaxInactiveInterval(60 * 60);
                String sessionId = session.getId();
                System.out.println(sessionId);
                LoginUserManager.getInstance().registSession(sessionId, user);
                Cookie cookieSId = new Cookie("JSESSIONID",sessionId);
                cookieSId.setMaxAge(60 * 60);
                response.addCookie(cookieSId);
            }else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.VALID_FAIL);
            }
        }

        writeJSONProtocol(response, jsonMap);
    }
}
