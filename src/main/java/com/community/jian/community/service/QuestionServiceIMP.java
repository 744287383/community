package com.community.jian.community.service;

import com.community.jian.community.mapper.QuestionMapping;
import com.community.jian.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionServiceIMP implements QuestionService{

    @Autowired
    private QuestionMapping questionMapping;
    @Override
    @Transactional
    public int insertQuestion(Question question) {

        return questionMapping.insertQuestion(question);
    }
}
