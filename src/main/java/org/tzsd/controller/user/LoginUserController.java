package org.tzsd.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.controller.BaseController;
import org.tzsd.pojo.UserDetailsInfo;
import org.tzsd.service.UserInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 处理登录后的用户请求的Controller
 */
@Controller
@RequestMapping("/user")
public class LoginUserController extends BaseController {

    @Resource(name = "userInfoService")
    private UserInfoService userInfoService;

    public UserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * @description: 获取用户详细信息
     * @param request
     * @param response
     */
    @RequestMapping("getUserDetailInfo")
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
}
