package com.community.jian.community.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @GetMapping("/register")
    public String  register(){

        return "register";
    }
    @PostMapping("/register")
    public String toRegister(){


        return "redirect:/";
    }

}
