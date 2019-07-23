package com.community.jian.community.service;

import com.community.jian.community.dto.QuestionDTO;
import com.community.jian.community.mapper.QuestionMapping;
import com.community.jian.community.mapper.UserMapping;
import com.community.jian.community.model.Question;
import com.community.jian.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceIMP implements QuestionService{
    @Autowired
    private UserMapping userMapping;

    @Autowired
    private QuestionMapping questionMapping;
    @Override
    @Transactional
    public int insertQuestion(Question question) {
        return questionMapping.insertQuestion(question);
    }

    @Override
    public List<QuestionDTO> list() {
        List<Question> questions=questionMapping.list();
        List<QuestionDTO> questionDTOs=new ArrayList<>();
        if (null!=questions&&0<questions.size())
        for (Question question: questions){
            User user = userMapping.findUserById(String.valueOf(question.getCreator()));
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
        return questionDTOs;
    }
}
