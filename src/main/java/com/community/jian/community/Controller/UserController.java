package com.community.jian.community.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/tag")
    public String  getUsers(){

        return "tagsTest";
    }

}
