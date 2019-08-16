package com.community.jian.community.model;

public class Notification {
    private Long id;

    private Long sender;

    private Long recipient;

    private Integer type;

    private Long autorid;

    private Integer status;

    private Long gmtCreate;

    private String senderName;

    private String autorTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getRecipient() {
        return recipient;
    }

    public void setRecipient(Long recipient) {
        this.recipient = recipient;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getAutorid() {
        return autorid;
    }

    public void setAutorid(Long autorid) {
        this.autorid = autorid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName == null ? null : senderName.trim();
    }

    public String getAutorTitle() {
        return autorTitle;
    }

    public void setAutorTitle(String autorTitle) {
        this.autorTitle = autorTitle == null ? null : autorTitle.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sender=").append(sender);
        sb.append(", recipient=").append(recipient);
        sb.append(", type=").append(type);
        sb.append(", autorid=").append(autorid);
        sb.append(", status=").append(status);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", senderName=").append(senderName);
        sb.append(", autorTitle=").append(autorTitle);
        sb.append("]");
        return sb.toString();
    }
}