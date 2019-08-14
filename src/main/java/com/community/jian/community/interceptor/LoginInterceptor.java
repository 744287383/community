package com.community.jian.community.interceptor;

import com.community.jian.community.dto.Pioneer;
import com.community.jian.community.model.User;
import com.community.jian.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Value("${github.client.client_id}")
    private String client_id;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

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
            return true;
        }else {
           return true;
        }



    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(null!=modelAndView) {
            Pioneer pioneer = new Pioneer();
            pioneer.setClient_id(client_id);
            Map<String, Object> model = modelAndView.getModel();
            model.put("pioneer", pioneer);
        }

    }

}
