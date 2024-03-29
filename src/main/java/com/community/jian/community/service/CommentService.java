package com.community.jian.community.service;

import com.community.jian.community.dto.CommentDTO;
import com.community.jian.community.model.Comment;
import com.community.jian.community.model.User;

import java.util.List;

public interface CommentService {
    int  insertComment(Comment comment);

    void addComment(Comment comment, User user);

    List<CommentDTO> getOneComment(Long questionId);

    List<CommentDTO> getTwoCommentDTOS(Long id);
}
