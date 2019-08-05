package com.community.jian.community.Controller;

import com.community.jian.community.dto.ResultDTO;
import com.community.jian.community.model.Comment;
import com.community.jian.community.model.User;
import com.community.jian.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CommentContorller {
    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public ResultDTO insertComment(@RequestBody Comment comment,
                                   HttpSession session){
        User user = (User) session.getAttribute("user");
        System.out.println(comment.toString());
        comment.setCommentor(6L);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        int i = commentService.insertComment(comment);
        if (i==1){
            System.out.println("插入超过");
        }
        ResultDTO resultDTO = ResultDTO.successOf();
        resultDTO.setData(comment);
        return resultDTO;
    }
}
