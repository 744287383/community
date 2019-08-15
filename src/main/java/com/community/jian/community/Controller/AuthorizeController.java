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
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(request.getScheme()+"://"+request.getServerName()+"/fallback");

        //请求Github的API获取accountid name bio
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);



        if (null != githubUser&&null!=githubUser.getId()) {
            User user=new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setBio(githubUser.getBio());
            user.setIconUrl(githubUser.getAvatar_url());
            user=userService.login(user);
            System.out.println(user.toString());
            request.getSession().setAttribute("user",user);
            Cookie cookie=new Cookie("token",user.getToken());
            cookie.setMaxAge(60*60);
            response.addCookie(cookie);
        return "redirect:/";
        } else {
            response.addCookie(new Cookie("token", null));
            return "redirect:/";
        }


    }
}
