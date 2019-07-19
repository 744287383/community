package com.community.jian.community.Controller;


import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("name","谭建");
        return "index";
    }
}
