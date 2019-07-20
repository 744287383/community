package com.community.jian.community.model;


public class User {

  private long id;
  private String accountId;
  private String name;
  private String bio;
  private String token;
  private String iconUrl;
  private long gmtCreate;
  private long gmtModified;

  public User() {
  }

  public User(long id, String accountId, String name, String bio, String token, String iconUrl, long gmtCreate, long gmtModified) {
    this.id = id;
    this.accountId = accountId;
    this.name = name;
    this.bio = bio;
    this.token = token;
    this.iconUrl = iconUrl;
    this.gmtCreate = gmtCreate;
    this.gmtModified = gmtModified;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }


  public String getIconUrl() {
    return iconUrl;
  }

  public void setIconUrl(String iconUrl) {
    this.iconUrl = iconUrl;
  }


  public long getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(long gmtCreate) {
    this.gmtCreate = gmtCreate;
  }


  public long getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(long gmtModified) {
    this.gmtModified = gmtModified;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", accountId='" + accountId + '\'' +
            ", name='" + name + '\'' +
            ", bio='" + bio + '\'' +
            ", token='" + token + '\'' +
            ", iconUrl='" + iconUrl + '\'' +
            ", gmtCreate=" + gmtCreate +
            ", gmtModified=" + gmtModified +
            '}';
  }
}
