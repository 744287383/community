package com.community.jian.community.Controller;

import com.alibaba.fastjson.JSONObject;
import com.community.jian.community.dto.ResultDTO;
import com.community.jian.community.exception.LocalUserLoginException;
import com.community.jian.community.exception.UserErrorMessage;
import com.community.jian.community.model.User;
import com.community.jian.community.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LocalUserLoginController {
    @Autowired
    private UserService userService;


    @PostMapping(value = "/localUserLogin")
    @ResponseBody
    public ResultDTO localUserLogin(@RequestBody JSONObject jsonObject,
                                    HttpServletRequest request,
                                    HttpServletResponse response){
        String phoneNum=jsonObject.getString("phoneNum");
        String password=jsonObject.getString("password");
        if (StringUtils.isBlank(phoneNum))
        {
            return  ResultDTO.errorOf(UserErrorMessage.USER_PHONE_NOT_NULL);
        }
        if (StringUtils.isBlank(password)){
            return  ResultDTO.errorOf(UserErrorMessage.USER_PASSWORD_NOT_NULL);
        }
       User user= userService.FindUSerByPhoneNum(phoneNum);
        if (user!=null&&password.equals(user.getPassword())){
            user.setToken(UUID.randomUUID().toString());
            userService.updateUser(user);
            request.getSession().setAttribute("user",user);
            response.addCookie(new Cookie("token",user.getToken()));
            return ResultDTO.successOf();
        }else{
          throw  new LocalUserLoginException(UserErrorMessage.USER_PASSWORD_NOT_MATCH);
        }

    }

}
