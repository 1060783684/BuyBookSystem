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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        //判断用户名和密码是否其中一个为空
        if(username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()){
            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.REGIST_UP_ISNON); //用户名密码为空
        }else {
            String unReg = "^[1][3,4,5,7,8][0-9]{9}$";//用户名格式必须是电话号码
            Pattern unPattern = Pattern.compile(unReg);
            Matcher unMatcher = unPattern.matcher(username);
            //判断用户名是否符合手机号格式
            if(unMatcher.matches()) {
                String reg = " "; //非法字符
                Pattern pattern = Pattern.compile(reg);
                Matcher matcher = pattern.matcher(password);
                //判断是否含有非法字符
                if (!matcher.find()) {
                    if(password.length() < 6 || password.length() > 16) {
                        jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.REGIST_PW_LENGTH_FAIL);
                    } else {
                        User user = userInfoService.getUser(username);
                        if (user != null) {
                            jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.REGIST_USERNAME_EXIST);
                        } else {
                            try {
                                long id = userInfoService.regist(username, password);
                                //使用用户名作为账户id
                                if (Long.valueOf(username).equals(id)) {
                                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.REGIST_SUCCESS);
                                } else {
                                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.REGIST_FAIL);
                                }
                            } catch (Exception e) { //报异常说明用户名有非法字符
                                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.REGIST_UN_CONTAIN_NONNUM);
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.REGIST_PW_CONTAIN_ILLCODE);
                }
            }else {
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.REGIST_UN_NONPHONE); //用户名不符合手机号格式
            }
        }
        //发送消息
        writeJSONProtocol(response, jsonMap);
    }
}
