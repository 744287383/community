package com.community.jian.community.model;

import lombok.Data;

@Data
public class User {

  private long id;
  private String accountId;
  private String name;
  private String bio;
  private String token;
  private String iconUrl;
  private long gmtCreate;
  private long gmtModified;

}
