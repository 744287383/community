package com.community.jian.community.exception;

public enum CommentErrorMessage implements ICutomizeMessage {
    NOT_LOGIN(3001,"游客无法进行评论，赶快去点击确认进行登录！"),
    NOT_FOUNT_QUESTION (3002,"评论的问题丢失了！"),
    COMMENT_TYPE_ERROR(3003,"无法确认改评论是一级评论还是二级评论！");

    private Integer code ;
    private String message;

    CommentErrorMessage(Integer code,String message) {
        this.code=code;
        this.message=message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
