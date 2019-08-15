package com.community.jian.community.Controller;

import com.community.jian.community.dto.CommentDTO;
import com.community.jian.community.dto.QuestionDTO;
import com.community.jian.community.service.CommentService;
import com.community.jian.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
   private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id,
                           Model model){
        questionService.addViewCount(id);
        QuestionDTO questionDTO=questionService.getQuestionDTOById(id);
        model.addAttribute("questionDTO",questionDTO);
        List<QuestionDTO> relateQuestion=questionService.getRelateQuestion(questionDTO.getId(),questionDTO.getTags());
        model.addAttribute("relateQuestion",relateQuestion);
        List<CommentDTO> commentDTOS =commentService.getOneComment(Long.valueOf(id));
        model.addAttribute("commentDTOS",commentDTOS);
    return "question";
    }





}
