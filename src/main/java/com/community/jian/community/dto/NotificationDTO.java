package com.community.jian.community.dto;

import com.community.jian.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;

    private Long sender;

    private Long recipient;

    private Integer type;

    private Long autorid;

    private Integer status;

    private Long gmtCreate;

    private String senderName;

    private String autorTitle;

    private String  typeName;

}