package com.community.jian.community.exception;

public enum NotificationErrorMessage implements ICutomizeMessage {

    NOTIFICATION_NOT_FOUND(5001,"该评论不翼而飞了,请换个试试把!" ),
    NOTIFICATION_NOT_RECIPIENT_MATCH(5002,"兄弟你在查看别人的通知呢!" );


    private Integer code ;
    private String message;

    NotificationErrorMessage(Integer code, String message) {
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
