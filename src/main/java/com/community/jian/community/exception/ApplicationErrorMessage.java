package com.community.jian.community.exception;

public enum ApplicationErrorMessage implements ICutomizeMessage {
    SERVICE_HOT(4001,"服务器冒烟了，要不然稍后再试试！！！"),
    REQUEST_ERROR(4002,"请求的地址有误，要不然换个试试！！！");


    private Integer code ;
    private String message;

    ApplicationErrorMessage(Integer code, String message) {
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
