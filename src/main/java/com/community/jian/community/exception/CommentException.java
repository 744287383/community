package com.community.jian.community.exception;



public class CommentException extends RuntimeException {
    private Integer code;
    private String message;

    public CommentException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommentException(ICutomizeMessage iCutomizeMessage) {
    this.code=iCutomizeMessage.getCode();
    this.message=iCutomizeMessage.getMessage();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public Integer getCode() {
        return this.code;
    }

}
