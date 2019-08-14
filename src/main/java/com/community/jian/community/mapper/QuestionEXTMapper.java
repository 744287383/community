package com.community.jian.community.mapper;

import com.community.jian.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface QuestionEXTMapper {
void addViewCount(Integer id);
Integer addCommentCount(Integer id);
List<Question> getRelateQuestion(@Param("id") Long id,@Param("tags") String tags);
}