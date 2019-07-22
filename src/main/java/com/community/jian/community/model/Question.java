package com.community.jian.community.model;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Question {

  private long id;
  @NotEmpty(message = "*问题标题不能为空")
  @Length(max = 50,message = "最多不能超过50个字符")
  private String title;
  @NotEmpty(message = "*问题内容不能为空")
  private String description;
  private long gmtCreate;
  private long gmtModified;
  private long commentCount;
  private long viewCount;
  private long likeCount;
  @NotEmpty(message = "*标签不能为空")
  private String tags;
  private long creator;

  public Question() {
  }

  public Question(long id, String title, String description, long gmtCreate, long gmtModified, long commentCount, long viewCount, long likeCount, String tags, long creator) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.gmtCreate = gmtCreate;
    this.gmtModified = gmtModified;
    this.commentCount = commentCount;
    this.viewCount = viewCount;
    this.likeCount = likeCount;
    this.tags = tags;
    this.creator = creator;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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


  public long getCommentCount() {
    return commentCount;
  }

  public void setCommentCount(long commentCount) {
    this.commentCount = commentCount;
  }


  public long getViewCount() {
    return viewCount;
  }

  public void setViewCount(long viewCount) {
    this.viewCount = viewCount;
  }


  public long getLikeCount() {
    return likeCount;
  }

  public void setLikeCount(long likeCount) {
    this.likeCount = likeCount;
  }


  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }


  public long getCreator() {
    return creator;
  }

  public void setCreator(long creator) {
    this.creator = creator;
  }

  @Override
  public String toString() {
    return "Question{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", gmtCreate=" + gmtCreate +
            ", gmtModified=" + gmtModified +
            ", commentCount=" + commentCount +
            ", viewCount=" + viewCount +
            ", likeCount=" + likeCount +
            ", tags='" + tags + '\'' +
            ", creator=" + creator +
            '}';
  }
}
