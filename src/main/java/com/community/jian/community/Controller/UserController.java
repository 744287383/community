package com.community.jian.community.Controller;

import com.community.jian.community.dto.UserDTO;
import com.community.jian.community.model.User;
import com.community.jian.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/register")
    public String  register(UserDTO userDTO){

        return "register";
    }
    @PostMapping("/register")
    public String toRegister(@Valid UserDTO userDTO, BindingResult bindingResult,
                             HttpServletRequest request,
                             HttpServletResponse response){

        if (bindingResult.hasErrors()){
            System.out.println("字段错误"+userDTO.toString());
        return "register";
        }
      boolean onlyPhone = userService.validateOnlyPhoneNum(userDTO.getPhoneNum());
        if (!onlyPhone){
            bindingResult.rejectValue("phoneNum","该号码已经被注册！","该号码已经被注册！");
            return "register";
        }
        User user= userService.registerUser(userDTO);
        if (user==null){
            return "redirect:/";
        }
        response.addCookie(new Cookie("token",user.getToken()));
        request.getSession().setAttribute("user",user);
        return "redirect:/";
    }

}
