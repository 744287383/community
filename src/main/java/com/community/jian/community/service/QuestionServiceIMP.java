package com.community.jian.community.service;

import com.community.jian.community.dto.PaginationDTO;
import com.community.jian.community.dto.QuestionDTO;
import com.community.jian.community.mapper.QuestionMapping;
import com.community.jian.community.mapper.UserMapping;
import com.community.jian.community.model.Question;
import com.community.jian.community.model.User;
import org.jetbrains.annotations.NotNull;
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
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO=new PaginationDTO();
        Integer count=questionMapping.countQuestion();
        paginationDTO.initPage(count,page,size);
        List<Question> questions=questionMapping.list(paginationDTO.getPage(),size);
        List<QuestionDTO> questionDTOs = getQuestionDTOS(questions);

        paginationDTO.setQuestionDTOs(questionDTOs);


        return paginationDTO;
    }

    @Override
    public PaginationDTO listById(Integer page, Integer size, Integer id) {
        PaginationDTO paginationDTO=new PaginationDTO();
        Integer count=questionMapping.countQuestionById(id);
        paginationDTO.initPage(count,page,size);
        List<Question> questions=questionMapping.listById(paginationDTO.getPage(),size,id);
        List<QuestionDTO> questionDTOs = getQuestionDTOS(questions);

        paginationDTO.setQuestionDTOs(questionDTOs);


        return paginationDTO;
    }

    public QuestionDTO getQuestionDTOById(Integer id){
        QuestionDTO questionDTO=new QuestionDTO();
        Question question=questionMapping.getQuestionById(id);
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapping.findUserById(String.valueOf(questionDTO.getCreator()));
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void createOrUpdateQuestion(Question question,User user) {
        if (question.getId()==0){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setCreator(user.getId());
            questionMapping.insertQuestion(question);
            return;
        }
        if (question.getId()>0){
            if (question.getCreator()!=user.getId()){
                return;
            }
            questionMapping.updateQuestion(question);
        }
    }

    @NotNull
    private List<QuestionDTO> getQuestionDTOS(List<Question> questions) {
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        if (null != questions && 0 < questions.size())
            for (Question question : questions) {
                User user = userMapping.findUserById(String.valueOf(question.getCreator()));
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOs.add(questionDTO);
            }
        return questionDTOs;
    }

}
