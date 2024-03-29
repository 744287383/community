package com.community.jian.community.service;

import ch.qos.logback.core.net.SyslogOutputStream;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public PaginationDTO list(String search,Integer page, Integer size) {
        if (StringUtils.isNoneBlank(search)){
            search=search.trim();
            search=toConversion(search);
            String[] s = StringUtils.split(search," ");
            search = Arrays.stream(s).collect(Collectors.joining("|"));
        }
        PaginationDTO<QuestionDTO> paginationDTO=new PaginationDTO<>();
        Integer count= questionEXTMapper.countSearchQuestion(search);
        int offset = paginationDTO.initPage(count, page, size);
        List<Question> questions=questionEXTMapper.getSearchQuestion(search,offset,paginationDTO.getSize());
        List<QuestionDTO> questionDTOs = getQuestionDTOS(questions);
        paginationDTO.setData(questionDTOs);
        return paginationDTO;
    }

    @Override//获取个人发布问题列表
    public PaginationDTO listById(Integer page, Integer size, Integer id) {
        PaginationDTO<QuestionDTO> paginationDTO=new PaginationDTO<>();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(id);
        Integer count=(int)questionMapper.countByExample(questionExample);
        int offset = paginationDTO.initPage(count, page, size);
        questionExample=new QuestionExample();

        questionExample.setOrderByClause("id desc");
        questionExample.createCriteria()
                .andCreatorEqualTo(id);
        List<Question> questions=questionMapper.selectByExampleWithRowbounds(questionExample,
                new RowBounds(offset,paginationDTO.getSize()));
        List<QuestionDTO> questionDTOs = getQuestionDTOS(questions);

        paginationDTO.setData(questionDTOs);


        return paginationDTO;
    }
    @Override//通过id获取问题详细
    public QuestionDTO getQuestionDTOById(Integer id){
        QuestionDTO questionDTO=new QuestionDTO();
        Question question=questionMapper.selectByPrimaryKey(Long.valueOf(id));
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

    @Override
    public List<QuestionDTO> getRelateQuestion(Long id,String tags) {
        tags=toConversion(tags);
        String s = tags.replace(",", "|");

        List<Question> questions = questionEXTMapper.getRelateQuestion(id,s);
        if (questions==null||questions.size()<=0){
            return new ArrayList<>();
        }
        List<QuestionDTO> collect = questions.stream().map(question -> {
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO1);
            return questionDTO1;
        }).collect(Collectors.toList());
        return collect;
    }

    private String toConversion(String tags) {
        String[] conversion_char={"\\","$", "(", ")", "*", "+", ".", "[", "]", "?",  "^", "{", "}", "|"};
        if (StringUtils.isNoneBlank(tags)){
            for (String conversion:conversion_char){
                if (tags.contains(conversion)){
                    return tags.replace(conversion, "\\" + conversion);//编译器会把\\转成\;
                }
            }
        }
        return tags;
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
