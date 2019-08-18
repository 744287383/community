package com.community.jian.community.exception;

public enum  UserErrorMessage implements ICutomizeMessage{
    USER_PHONE_NOT_NULL(6001,"账号不能为空！"),
    USER_PASSWORD_NOT_NULL(6002,"密码不能为空！"),
    USER_PASSWORD_NOT_MATCH(6003,"账户和密码不匹配！");
    private Integer code;
    private String message;

    UserErrorMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
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
