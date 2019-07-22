package com.community.jian.community.model;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class Question {

    private long id;
    @NotBlank(message = "*问题标题不能为空")
    @Length(max = 50, message = "最多不能超过50个字符")
    private String title;
    @NotBlank(message = "*问题内容不能为空")
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private long commentCount;
    private long viewCount;
    private long likeCount;
    @NotBlank(message = "*标签不能为空")
    private String tags;
    private long creator;


}
