package com.community.jian.community.service;

import com.community.jian.community.dto.PaginationDTO;
import com.community.jian.community.dto.QuestionDTO;
import com.community.jian.community.exception.QuestionErrorMessage;
import com.community.jian.community.exception.ServiceException;
import com.community.jian.community.mapper.QuestionEXTMapper;
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
    private QuestionEXTMapper questionEXTMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Override
    @Transactional
    public int insertQuestion(Question question) {
        return questionMapper.insert(question);
    }

    @Override
    //获取首页问题列表
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

    @Override//获取个人发布问题列表
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
    @Override//通过id获取问题详细
    public QuestionDTO getQuestionDTOById(Integer id){
        QuestionDTO questionDTO=new QuestionDTO();
        Question question=questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw new ServiceException(QuestionErrorMessage.QUESTION_NOT_FOUND);
        }
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
        if (question.getId()!=null){
            if (question.getCreator()!=Math.toIntExact(user.getId())){
               throw new ServiceException(QuestionErrorMessage.QUESTION_NOT_MATCH);
            }
            question.setGmtModified(System.currentTimeMillis());
            question.setCreator(null);

            int i = questionMapper.updateByPrimaryKeySelective(question);
            if(i!=1){
                throw new ServiceException(QuestionErrorMessage.QUESTION_ERROR_UPDATE);
            }
        }
    }

    @Override
    public void addViewCount(Integer id) {
        questionEXTMapper.addViewCount(id);
    }

    @NotNull//把question的list转换成questionDTO的list
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
