package com.community.jian.community.service;

import com.community.jian.community.dto.QuestionDTO;
import com.community.jian.community.model.Question;

import java.util.List;

public interface QuestionService {
    int insertQuestion(Question question);

    List<QuestionDTO> list();
}
