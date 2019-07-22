package com.community.jian.community.webFilter;

import com.community.jian.community.model.User;
import com.community.jian.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter(filterName = "LoginByTokenFilter",urlPatterns = "*")
public class LoginByTokenFilter implements Filter {
    @Autowired
    private UserService userService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        User user = (User) request.getSession().getAttribute("user");
        if (null==user){
            Cookie[] cookies=request.getCookies();
            Map<String,Cookie> cookieMap=new HashMap<>();
            if (cookies!=null&&cookies.length>0){
                for (Cookie cookie:cookies){
                    cookieMap.put(cookie.getName(),cookie);
                }
            }
            Cookie cookie1=cookieMap.get("token");
            String token=cookie1==null?null:cookie1.getValue();


            if (null!=token){
                 user=userService.findUserByToken(token);
                if (null!=user){
                    request.getSession().setAttribute("user",user);
                }
            }
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
