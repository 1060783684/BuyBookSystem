package org.tzsd.filter;

import org.tzsd.manager.LoginUserManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        //若用户不存在,则重定向到登陆页面
        if(!LoginUserManager.getInstance().getUsers().containsKey(sessionId)){
            request.getRequestDispatcher("/view/login.html").forward(req,resp);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
