package com.community.jian.community.mapper;

import com.community.jian.community.model.Question;
import org.apache.ibatis.annotations.*;
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

    @Select("select * from question where creator=#{id} order by id desc limit #{size}*(#{page}-1),#{size}")
    List<Question> listById(@Param("page") Integer page,@Param("size") Integer size,@Param("id") Integer id);
    @Select("select count(id) from question where creator=#{id}")
    int countQuestionById(@Param("id") Integer id);
    @Select("select * from question where id =#{id}")
    Question getQuestionById(@Param("id")Integer id);
    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tags=#{tags} where id=#{id} and creator=#{creator}")
    int updateQuestion(Question question);
}
