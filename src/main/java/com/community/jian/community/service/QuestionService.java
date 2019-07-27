package com.community.jian.community.service;

import com.community.jian.community.dto.PaginationDTO;
import com.community.jian.community.dto.QuestionDTO;
import com.community.jian.community.model.Question;
import com.community.jian.community.model.User;

public interface QuestionService {
    int insertQuestion(Question question);

    PaginationDTO list(Integer page, Integer size);

    PaginationDTO listById(Integer page, Integer size,Integer id);
    QuestionDTO getQuestionDTOById(Integer id);



    void createOrUpdateQuestion(Question question, User user);
}
