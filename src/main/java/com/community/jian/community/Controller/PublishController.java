package com.community.jian.community.Controller;

import com.community.jian.community.model.Question;
import com.community.jian.community.model.User;
import com.community.jian.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;




    @GetMapping("/publish")
    public String publish(Model model, Question question) {

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Model model,
                            @Valid Question question,
                            BindingResult bindingResult,
                            HttpServletRequest request) {


        //校验出错时返回原来的页面
        if (bindingResult.hasErrors()) {
            return "publish";
        }

        question.setCreator(((User) request.getSession().getAttribute("user")).getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        System.out.println(question.toString());
        questionService.insertQuestion(question);
        return "redirect:/";
    }

}
