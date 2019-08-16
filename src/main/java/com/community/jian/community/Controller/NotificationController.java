package com.community.jian.community.Controller;

import com.community.jian.community.dto.CommentDTO;
import com.community.jian.community.dto.QuestionDTO;
import com.community.jian.community.model.User;
import com.community.jian.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/toRead/{id}")
    public String question(@PathVariable("id") Long id, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        Long questionId=notificationService.modifyStatus(id,user);
        return "redirect:/question/"+questionId;
    }
}
