package org.tzsd.filter;

import org.json.JSONObject;
import org.tzsd.constance.JSONProtocolConstance;
import org.tzsd.manager.LoginUserManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 访问特殊页面时判断用户是否登录
 */
@WebFilter(filterName = "UserLoginFilter")
public class UserLoginFilter implements Filter {
    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        String sessionId = request.getSession().getId();
        //若用户不存在,则重定向到登陆页面(forword不好使)
        System.out.println( "[" + sessionId + "] : "+request.getRequestURL() + " redirect!");
        if(!LoginUserManager.getInstance().getUsers().containsKey(sessionId)){
            System.err.println( "[" + sessionId + "] : "+request.getRequestURL() + " redirect!");
            HttpServletResponse response = (HttpServletResponse) resp;
            if(request.getMethod().equals("POST")){
                Map<String, Object> jsonMap = new HashMap<String, Object>();
                jsonMap.put(JSONProtocolConstance.RESULT, JSONProtocolConstance.POST_REDIRECT);
                jsonMap.put(JSONProtocolConstance.REDIRECT, "/view/login.html");
                JSONObject jsonObject = new JSONObject(jsonMap);
                try {
                    Writer writer = response.getWriter();
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    return;
                }
            }else {
                response.sendRedirect("/view/login.html");
            }
            //当用户没有登陆时不考虑之后的过滤器
            return ;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
