package com.community.jian.community.exception;

public enum UploadFileMessage implements ICutomizeMessage {
    UPLOAD_FILE_SUCCESS(1,"上传成功"),
    UPLOAD_FILE_ERROR(0,"上传失败"),
    NOT_SUPPORT_FILE_TYPE(0,"上传失败,不知道你上传的是什么！"),
    UNKNOWN_ERROR(0,"上传失败,未知错误请联系站长！"),
    FILE_NOT_NULL(0,"上传失败,你上传的文件是空的！"),;
private Integer code;
private String message;

    UploadFileMessage(Integer code, String message) {
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
