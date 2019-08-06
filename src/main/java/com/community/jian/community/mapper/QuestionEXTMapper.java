package com.community.jian.community.mapper;

import org.springframework.stereotype.Component;

@Component
public interface QuestionEXTMapper {
void addViewCount(Integer id);
Integer addCommentCount(Integer id);
}