package com.community.jian.community.mapper;

import com.community.jian.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface QuestionMapping {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tags) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tags})")
    int insertQuestion(Question question);
    @Select("select * from question order by id desc limit #{size}*(#{page}-1),#{size}")
    List<Question> list(@Param("page") Integer page,@Param("size") Integer size);
    @Select("select count(id) from question")
    int countQuestion();
}
