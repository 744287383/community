package com.community.jian.community.Controller;

import com.community.jian.community.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @PostMapping("/nini")
    public String  getUsers(User user){
        //收到的参数转为对象的属性
        System.out.println(user.toString());
        return "nav";
    }
}
