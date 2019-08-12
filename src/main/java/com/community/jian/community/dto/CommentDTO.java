package com.community.jian.community.dto;

import com.community.jian.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;

    private Long parentId;

    private Long commentor;

    private Integer type;

    private String content;

    private Long gmtCreate;

    private Long gmtModified;

    private Long likeCount;

    private Long commentCount;

    private Long toId;

    private User user;

}