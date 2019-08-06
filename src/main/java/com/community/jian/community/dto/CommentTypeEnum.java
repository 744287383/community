package com.community.jian.community.dto;

public enum CommentTypeEnum {
    Comment_TYPE_FATHER(1),
    Comment_TYPE_SON(2);
    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isCommentType(Integer type) {
        if (type==CommentTypeEnum.Comment_TYPE_FATHER.getType()){
            return true;
        }

        if (type==CommentTypeEnum.Comment_TYPE_SON.getType()){
            return true;
        }

        return false;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
