package com.community.jian.community.Controller;


import com.community.jian.community.model.User;
import com.community.jian.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;
    @Value("${github.loginSuccess.redirect.url}")
    private String redirectUrl;
    @Value("${github.client.client_id}")
    private String client_id;
    @RequestMapping("/")
    public String index(Model model,
                        HttpServletRequest request){
        model.addAttribute("redirectUrl",redirectUrl);
        model.addAttribute("client_id",client_id);
        return "index";
    }

    @RequestMapping("/logout")
    public String index1(Model model, HttpServletRequest request,
                         HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        user.setToken(null);
        userService.updateUser(user);
        request.getSession().setAttribute("user",null);
        Cookie cookie=new Cookie("token",null);

        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);


        return "redirect:/";
    }

}
