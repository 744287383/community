package com.community.jian.community.dto;

public enum NotificationStatusEum {
    READ(1),
    UNREAD(0);
    private Integer status;

    NotificationStatusEum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
