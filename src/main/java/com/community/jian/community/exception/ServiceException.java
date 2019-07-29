package com.community.jian.community.exception;

public class ServiceException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ServiceException(String message) {
        super(message);
        this.message = message;
    }
    public ServiceException(ICutomizeMessage cutomizeMessage){
        this.message=cutomizeMessage.getMessage();
    }

}
