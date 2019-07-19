package com.community.jian.community.Controller;

import com.community.jian.community.dto.AccessTokenDTO;
import com.community.jian.community.dto.GithubUser;
import com.community.jian.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {
    @Autowired
   private  GithubProvider githubProvider;
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
                                 HttpServletRequest request){
        model.addAttribute("redirectUrl",redirectUrl);
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        String accessToken=githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.toString());
        if (user!=null){
            request.getSession().setAttribute("user",user);
            return  "redirect:/";
        }else {
            return  "redirect:/";
        }



    }
}
