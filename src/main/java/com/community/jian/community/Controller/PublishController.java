package com.community.jian.community.Controller;

import com.community.jian.community.dto.QuestionDTO;
import com.community.jian.community.dto.TagsDTO;
import com.community.jian.community.exception.QuestionErrorMessage;
import com.community.jian.community.exception.ServiceException;
import com.community.jian.community.model.Question;
import com.community.jian.community.model.User;
import com.community.jian.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private TagsDTO tagsDTO;

    @GetMapping("/editQuestion/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, HttpSession session){
        QuestionDTO questionDTO = questionService.getQuestionDTOById(id);
        User user= (User) session.getAttribute("user");

        if (questionDTO.getCreator() !=Math.toIntExact(user.getId())){
            throw new ServiceException(QuestionErrorMessage.QUESTION_NOT_MATCH);
        }
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("actionName","编辑");
        model.addAttribute("action","提交");
        model.addAttribute("tagsDTO",tagsDTO);
        return "publish";
    }


    @GetMapping("/publish")
    public String publish(Model model, QuestionDTO questionDTO) {
        model.addAttribute("actionName","发起");
        model.addAttribute("action","发布");
        model.addAttribute("tagsDTO",tagsDTO);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(Model model,
                            @Valid QuestionDTO questionDTO,
                            BindingResult bindingResult,
                            HttpServletRequest request) {

        Question question=new Question();
        //校验出错时返回原来的页面
        if (bindingResult.hasErrors()) {
            model.addAttribute("tagsDTO",tagsDTO);
            if (questionDTO.getId()!=null){
                model.addAttribute("actionName","编辑");
                model.addAttribute("action","提交");
            }else {
                model.addAttribute("actionName","发起");
                model.addAttribute("action","发布");
            }
            return "publish";
        }

        BeanUtils.copyProperties(questionDTO,question);
        User user = (User) request.getSession().getAttribute("user");
        if (null!=user) {
            questionService.createOrUpdateQuestion(question, user);
        }
        return "redirect:/";
    }

}
