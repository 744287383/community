package com.community.jian.community.service;

import com.community.jian.community.dto.CommentTypeEnum;
import com.community.jian.community.exception.CommentErrorMessage;
import com.community.jian.community.exception.CommentException;
import com.community.jian.community.mapper.CommentMapper;
import com.community.jian.community.mapper.QuestionEXTMapper;
import com.community.jian.community.mapper.QuestionMapper;
import com.community.jian.community.model.Comment;
import com.community.jian.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceIMP implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionEXTMapper questionEXTMapper;
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

        Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
        if (null==question){
            throw new CommentException(CommentErrorMessage.NOT_FOUNT_QUESTION);
        }

        if (!CommentTypeEnum.isCommentType(comment.getType())){
        throw new CommentException(CommentErrorMessage.COMMENT_TYPE_ERROR);
        }

        if (CommentTypeEnum.Comment_TYPE_FATHER.getType()==comment.getType()){
            //一级评论
            int i = commentMapper.insertSelective(comment);
            if (1==i){
                questionEXTMapper.addCommentCount(Math.toIntExact(comment.getParentId()));
            }
        }else {
            //二级评论
            commentMapper.insertSelective(comment);
        }

    }
}
