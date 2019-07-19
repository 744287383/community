package com.community.jian.community.Controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Value("${github.loginSuccess.redirect.url}")
    private String redirectUrl;
    @Value("${github.client.client_id}")
    private String client_id;
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("redirectUrl",redirectUrl);
        model.addAttribute("client_id",client_id);
        return "index";
    }

    @RequestMapping("/logout")
    public String index1(Model model, HttpServletRequest request){
        request.getSession().removeAttribute("user");

        return "redirect:/";
    }

}
