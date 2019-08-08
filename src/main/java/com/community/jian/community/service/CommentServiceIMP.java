package com.community.jian.community.service;

import com.community.jian.community.dto.CommentDTO;
import com.community.jian.community.dto.CommentTypeEnum;
import com.community.jian.community.exception.CommentErrorMessage;
import com.community.jian.community.exception.CommentException;
import com.community.jian.community.mapper.CommentMapper;
import com.community.jian.community.mapper.QuestionEXTMapper;
import com.community.jian.community.mapper.QuestionMapper;
import com.community.jian.community.mapper.UserMapper;
import com.community.jian.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentServiceIMP implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionEXTMapper questionEXTMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public int  insertComment(Comment comment) {

        return commentMapper.insertSelective(comment);
    }

    @Override
    @Transactional
    public void addComment(Comment comment) {

        if (null==comment.getParentId()||0==comment.getParentId()){
            throw new CommentException(CommentErrorMessage.NOT_FOUNT_QUESTION);
        }

        if (!CommentTypeEnum.isCommentType(comment.getType())){
        throw new CommentException(CommentErrorMessage.COMMENT_TYPE_ERROR);
        }

        if (CommentTypeEnum.Comment_TYPE_FATHER.getType()==comment.getType()){
            //一级评论
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (null==question){
                throw new CommentException(CommentErrorMessage.NOT_FOUNT_QUESTION);
            }
            int i = commentMapper.insertSelective(comment);
            if (1==i){
                questionEXTMapper.addCommentCount(Math.toIntExact(comment.getParentId()));
            }
        }else {
            //二级评论
            Comment comment1 = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (comment1==null){
                throw  new CommentException(CommentErrorMessage.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
        }

    }

    @Override
    public List<CommentDTO> getOneComment(Long questionId) {
        CommentExample commentExample = new CommentExample();

        commentExample.or().andParentIdEqualTo(questionId)
                .andTypeEqualTo(CommentTypeEnum.Comment_TYPE_FATHER.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments==null||comments.size()==0){
            return  new ArrayList<CommentDTO>();
        }
        List<Long> userId = comments.stream().map(comment -> comment.getCommentor()).distinct().collect(Collectors.toList());
        UserExample userExample=new UserExample();
        userExample.or().andIdIn(userId);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            User user = userMap.get(comment.getCommentor());
            commentDTO.setUser(user);
            return commentDTO;
        }).collect(Collectors.toList());


        return commentDTOS;
    }
}
