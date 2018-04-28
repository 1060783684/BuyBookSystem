package org.tzsd.filter;

import org.hibernate.Session;
import org.tzsd.dao.StoreDAO;
import org.tzsd.manager.LoginUserManager;
import org.tzsd.pojo.Store;
import org.tzsd.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 进入店铺主界面之前的判断，判断其是否拥有店铺
 */
@WebFilter(filterName = "EntryStoreMainPageFilter")
public class EntryStoreMainPageFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        StoreDAO storeDAO = new StoreDAO();
        HttpServletRequest request = (HttpServletRequest)req;
        String sessionId = request.getSession().getId();
        User user = LoginUserManager.getInstance().getUsers().get(sessionId);
        //怕多线程情况下前一个判断成功，这里判断出错
        if(user == null){
            System.err.println( "[" + sessionId + "] : "+request.getRequestURL() + " redirect!");
            HttpServletResponse response = (HttpServletResponse) resp;
            response.sendRedirect("/view/login.html");
        }
        Store store = storeDAO.getStoreByUserId(user.getId());
        if(store == null){ //还没申请店铺
            request.getRequestDispatcher("/view/safety/store/storeApply.html").forward(req,resp);
        }else if(store.getIsCheck() == Store.NO){ //还在审核
            request.getRequestDispatcher("/view/safety/store/storeCheck.html").forward(req,resp);
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
