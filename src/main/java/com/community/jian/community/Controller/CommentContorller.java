package com.community.jian.community.Controller;

import com.community.jian.community.dto.CommentDTO;
import com.community.jian.community.dto.ResultDTO;
import com.community.jian.community.exception.CommentErrorMessage;
import com.community.jian.community.exception.CommentException;
import com.community.jian.community.model.Comment;
import com.community.jian.community.model.User;
import com.community.jian.community.service.CommentService;
import org.springframework.beans.BeanUtils;
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
    public ResultDTO insertComment(@RequestBody CommentDTO commentDTO,
                                   HttpSession session){
        User user = (User) session.getAttribute("user");
        if (null==user){
            throw new CommentException(CommentErrorMessage.NOT_LOGIN);
        }
        System.out.println(commentDTO.toString());


        Comment comment=new Comment();
        BeanUtils.copyProperties(commentDTO,comment);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentor(user.getId());

        commentService.addComment(comment);

        ResultDTO resultDTO = ResultDTO.successOf();
        resultDTO.setData(comment);
        return resultDTO;
    }
}
