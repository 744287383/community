package com.community.jian.community.dto;

import com.community.jian.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private long id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private long commentCount;
    private long viewCount;
    private long likeCount;
    private String tags;
    private long creator;
    private User user;
}
