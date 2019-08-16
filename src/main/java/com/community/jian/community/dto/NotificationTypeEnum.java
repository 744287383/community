package com.community.jian.community.dto;

import lombok.Data;



public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论")
    ;
    private Integer type;
    private String typeName;

    NotificationTypeEnum(Integer type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public Integer getType() {
        return this.type;
    }

    public  String getTypeName() {
        return this.typeName;
    }
    public static String getTypeName(Integer type) {
        if (type==NotificationTypeEnum.REPLY_QUESTION.getType())
        {
            return NotificationTypeEnum.REPLY_QUESTION.getTypeName();
        }
        if (type==NotificationTypeEnum.REPLY_COMMENT.getType()){
            return NotificationTypeEnum.REPLY_COMMENT.getTypeName();
        }
        return null;
    }
}
