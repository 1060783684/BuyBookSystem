package org.tzsd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.manager.LoginUserManager;
import org.tzsd.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 主页检测是否登录
 */
@Controller
public class MainPageLoadController extends BaseController{

    /**
     * @description: 主页检测是否登录
     * @param request
     * @param response
     */
    @RequestMapping("mainPageLoad.do")
    public void mainPageLoad(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        User user = LoginUserManager.getInstance().getUsers().get(session.getId());
        Map<String, Object> jsonMap = new HashMap();
        if(user == null){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_FAIL);
        }else {
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.RESULT_SUCCESS);
            jsonMap.put(JSONProtocolConstance.USERNAME, user.getName());
        }
        writeJSONProtocol(response, jsonMap);
    }
}
