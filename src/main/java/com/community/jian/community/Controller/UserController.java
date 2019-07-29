package com.community.jian.community.Controller;

import com.community.jian.community.mapper.UserMapper;
import com.community.jian.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    UserMapper userMapper;

    @GetMapping(value = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User getUserInfo(User user){
        //收到的参数转为对象的属性
        return user;
    }
}
