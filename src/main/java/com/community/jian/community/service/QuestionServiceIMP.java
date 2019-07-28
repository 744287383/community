package com.community.jian.community.service;

import com.community.jian.community.dto.PaginationDTO;
import com.community.jian.community.dto.QuestionDTO;
import com.community.jian.community.mapper.QuestionMapper;
import com.community.jian.community.mapper.UserMapper;
import com.community.jian.community.model.Question;
import com.community.jian.community.model.QuestionExample;
import com.community.jian.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;
    @Override
    @Transactional
    public int insertQuestion(Question question) {
        return questionMapper.insert(question);
    }

    @Override
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO=new PaginationDTO();
        Integer count= (int)questionMapper.countByExample(new QuestionExample());
        paginationDTO.initPage(count,page,size);
        int offset=paginationDTO.getSize()*(paginationDTO.getPage()-1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("id desc");
        List<Question> questions=questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,paginationDTO.getSize()));
        List<QuestionDTO> questionDTOs = getQuestionDTOS(questions);

        paginationDTO.setQuestionDTOs(questionDTOs);


        return paginationDTO;
    }

    @Override
    public PaginationDTO listById(Integer page, Integer size, Integer id) {
        PaginationDTO paginationDTO=new PaginationDTO();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(id);
        Integer count=(int)questionMapper.countByExample(questionExample);
        paginationDTO.initPage(count,page,size);
        int offset=paginationDTO.getSize()*(paginationDTO.getPage()-1);
        questionExample=new QuestionExample();
        questionExample.setOrderByClause("id desc");
        questionExample.createCriteria()
                .andCreatorEqualTo(id);
        List<Question> questions=questionMapper.selectByExampleWithRowbounds(questionExample,
                new RowBounds(offset,paginationDTO.getSize()));
        List<QuestionDTO> questionDTOs = getQuestionDTOS(questions);

        paginationDTO.setQuestionDTOs(questionDTOs);


        return paginationDTO;
    }

    public QuestionDTO getQuestionDTOById(Integer id){
        QuestionDTO questionDTO=new QuestionDTO();
        Question question=questionMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.selectByPrimaryKey(Long.valueOf(questionDTO.getCreator()));
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void createOrUpdateQuestion(Question question,User user) {
        if (question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setCreator(Math.toIntExact(user.getId()));
            questionMapper.insertSelective(question);
            return;
        }
        if (question.getId()>0){
            if (question.getCreator()!=Math.toIntExact(user.getId())){
                return;
            }
            question.setGmtModified(System.currentTimeMillis());
            question.setCreator(null);
            questionMapper.updateByPrimaryKeySelective(question);
        }
    }

    @NotNull
    private List<QuestionDTO> getQuestionDTOS(List<Question> questions) {
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        if (null != questions && 0 < questions.size())
            for (Question question : questions) {
                User user = userMapper.selectByPrimaryKey(Long.valueOf(question.getCreator()));
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOs.add(questionDTO);
            }
        return questionDTOs;
    }

}
