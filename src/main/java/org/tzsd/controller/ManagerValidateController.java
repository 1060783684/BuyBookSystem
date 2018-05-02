package org.tzsd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.service.ManagerService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 管理员登陆操作
 */
@Controller
public class ManagerValidateController extends BaseController {

    @Resource(name = "managerService")
    private ManagerService managerService;

    public ManagerService getManagerService() {
        return managerService;
    }

    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    /**
     * @description: 管理员登陆验证
     * @param request
     * @param response
     */
    @RequestMapping("/managerValidate.do")
    public void managerValidate(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String, Object> jsonMap = new HashMap();
        if(username == null || password == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.VALID_FAIL);
        }else {
            if(getManagerService().validate(username, password)){
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.VALID_SUCCESS);
                HttpSession session = request.getSession(true);
                session.setMaxInactiveInterval(60 * 60);
                String sessionId = session.getId();
                session.setAttribute("username",username); //添加验证参数
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
