package com.community.jian.community.service;

import com.community.jian.community.dto.PaginationDTO;
import com.community.jian.community.model.Question;

public interface QuestionService {
    int insertQuestion(Question question);

    PaginationDTO list(Integer page, Integer size);
}
