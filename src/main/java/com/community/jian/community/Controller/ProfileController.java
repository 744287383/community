package com.community.jian.community.Controller;

import com.community.jian.community.dto.NotificationDTO;
import com.community.jian.community.dto.PaginationDTO;
import com.community.jian.community.dto.QuestionDTO;
import com.community.jian.community.model.Notification;
import com.community.jian.community.model.Question;
import com.community.jian.community.model.User;
import com.community.jian.community.service.NotificationService;
import com.community.jian.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping(value = "/profile/{action}")
    public  String profile(@PathVariable(name = "action")String section, Model model,
                           @RequestParam(name = "page",defaultValue = "1") Integer page,
                           @RequestParam(name = "size",defaultValue = "7") Integer size, HttpSession session){

        User user = (User) session.getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        if("questions".equals(section)) {
            model.addAttribute("section", section);
            model.addAttribute("sectionName", "我的提问");
            PaginationDTO<QuestionDTO> paginationDTO = questionService.listById(page,size, Math.toIntExact(user.getId()));
            model.addAttribute("paginationDTO",paginationDTO);
        }
        if ("replies".equals(section)){
            model.addAttribute("section", section);
            model.addAttribute("sectionName", "最新回复");
            PaginationDTO<NotificationDTO> paginationDTO= notificationService.listByRecipient(page,size,user.getId());
            model.addAttribute("paginationDTO",paginationDTO);
        }
        return "profile";
    }
}
