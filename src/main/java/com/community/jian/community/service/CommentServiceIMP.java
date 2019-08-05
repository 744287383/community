package com.community.jian.community.service;

import com.community.jian.community.mapper.CommentMapper;
import com.community.jian.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceIMP implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public int  insertComment(Comment comment) {

        return commentMapper.insertSelective(comment);
    }
}
