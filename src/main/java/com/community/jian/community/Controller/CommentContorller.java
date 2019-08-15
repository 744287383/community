package com.community.jian.community.Controller;

import com.community.jian.community.dto.CommentDTO;
import com.community.jian.community.dto.ResultDTO;
import com.community.jian.community.exception.CommentErrorMessage;
import com.community.jian.community.exception.CommentException;
import com.community.jian.community.model.Comment;
import com.community.jian.community.model.User;
import com.community.jian.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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
        if (commentDTO==null|| StringUtils.isBlank(commentDTO.getContent())){
            throw new CommentException(CommentErrorMessage.COMMENT_CONTENT_NULL);
        }

        Comment comment=new Comment();
        BeanUtils.copyProperties(commentDTO,comment);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentor(user.getId());

        commentService.addComment(comment);



        return ResultDTO.successOf();
    }

    @GetMapping("/comment/{id}")
    @ResponseBody
    public ResultDTO getTwoComment(@PathVariable("id") Long id){
       List<CommentDTO>  commentDTOS=commentService.getTwoCommentDTOS(id);
        return ResultDTO.successOf(commentDTOS);
    }
}
