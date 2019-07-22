package com.community.jian.community.Controller;

import com.community.jian.community.dto.AccessTokenDTO;
import com.community.jian.community.dto.GithubUser;
import com.community.jian.community.model.User;
import com.community.jian.community.provider.GithubProvider;
import com.community.jian.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserService userService;

    @Value("${github.client.client_id}")
    private String client_id;
    @Value("${github.client.client_secret}")
    private String client_secret;
    @Value("${github.loginSuccess.redirect.url}")
    private String redirectUrl;

    @RequestMapping("/fallback")

    public String fallback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           Model model,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        model.addAttribute("redirectUrl", redirectUrl);
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUrl);

        //请求Github的API获取accountid name bio
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user1 = githubProvider.getUser(accessToken);


        if (null != user1) {
            User pojo = userService.findUserByAccountID(String.valueOf(user1.getId()));
            if (null != pojo) {
                //存在用户则跟新用户和token
                pojo.setBio(user1.getBio());
                String str = UUID.randomUUID().toString();
                pojo.setToken(str);
                pojo.setName(user1.getName());
                pojo.setIconUrl(user1.getAvatar_url());
                System.out.println(userService.updateUser(pojo));

                response.addCookie(new Cookie("token", str));
                request.getSession().setAttribute("user", pojo);
            } else {
                //不存在用户则注册到数据库
                pojo = new User();
                pojo.setAccountId(String.valueOf(user1.getId()));
                pojo.setBio(user1.getBio());
                pojo.setName(user1.getName());
                String str = UUID.randomUUID().toString();
                pojo.setToken(str);
                pojo.setIconUrl(user1.getAvatar_url());
                response.addCookie(new Cookie("token", str));
                userService.insertUser(pojo);
                System.out.println(pojo.toString());
                request.getSession().setAttribute("user", pojo);
            }

            return "redirect:";
        } else {
            response.addCookie(new Cookie("token", null));
            return "redirect:";
        }


    }
}
