package com.community.jian.community.Controller;

import com.community.jian.community.model.Question;
import com.community.jian.community.model.User;
import com.community.jian.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Value("${github.loginSuccess.redirect.url}")
    private String redirectUrl;
    @Value("${github.client.client_id}")
    private String client_id;


    @GetMapping("/publish")
    public String publish(Model model,Question question){
        model.addAttribute("redirectUrl",redirectUrl);
        model.addAttribute("client_id",client_id);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Model model,
                            @Valid Question question,
                             BindingResult bindingResult,
                            HttpServletRequest request){

        model.addAttribute("redirectUrl",redirectUrl);
        model.addAttribute("client_id",client_id);
        if (bindingResult.hasErrors()){
            System.out.println(bindingResult.getFieldErrors().toString());
            return "publish";
        }
        question.setCreator(((User)request.getSession().getAttribute("user")).getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        System.out.println(question.toString());
        questionService.insertQuestion(question);
        return "redirect:/";
    }

}
