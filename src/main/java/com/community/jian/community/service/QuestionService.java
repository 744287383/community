package com.community.jian.community.service;

import com.community.jian.community.dto.PaginationDTO;
import com.community.jian.community.dto.QuestionDTO;
import com.community.jian.community.model.Question;
import com.community.jian.community.model.User;

import java.util.List;

public interface QuestionService {
    int insertQuestion(Question question);

    PaginationDTO listById(Integer page, Integer size,Integer id);
    QuestionDTO getQuestionDTOById(Integer id);



    void createOrUpdateQuestion(Question question, User user);

    void addViewCount(Integer id);

    List<QuestionDTO> getRelateQuestion(Long id,String tags);

    PaginationDTO list(String search, Integer page, Integer size);
}
