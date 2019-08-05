package com.community.jian.community.exception;

public enum CommentErrorMessage implements ICutomizeMessage {
    NOT_LOGIN(3001,"游客无法进行评论，赶快去注册个账户吧！");

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
