package com.community.jian.community.Controller;

import com.community.jian.community.dto.PaginationDTO;
import com.community.jian.community.model.User;
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
    @GetMapping(value = "/profile/{action}")
    public  String profile(@PathVariable(name = "action")String section, Model model,
                           @RequestParam(name = "page",defaultValue = "1") Integer page,
                           @RequestParam(name = "size",defaultValue = "7") Integer size, HttpSession session){
        PaginationDTO paginationDTO=new PaginationDTO();
        if("questions".equals(section)) {
            model.addAttribute("section", section);
            model.addAttribute("sectionName", "我的提问");
            User user = (User) session.getAttribute("user");
            paginationDTO = questionService.listById(page,size, Math.toIntExact(user.getId()));
            model.addAttribute("paginationDTO",paginationDTO);
        }
        if ("replies".equals(section)){
            model.addAttribute("section", section);
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("paginationDTO",paginationDTO);
        }
        return "profile";
    }
}
