package com.community.jian.community.exception;

public enum QuestionErrorMessage implements ICutomizeMessage {
    QUESTION_NOT_FOUND("你找到的问题现在已经不在了，要不然换个试试！！"),
    QUESTION_ERROR_UPDATE("你更新的问题现在已经不在了，要不然换个试试！！"),
    QUESTION_NOT_MATCH("该问题不属于你的发布，禁止修改，换一个试试吧！！！");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    QuestionErrorMessage(String message) {
        this.message = message;
    }
}
