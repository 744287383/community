package com.community.jian.community.service;

import com.community.jian.community.dto.UserDTO;
import com.community.jian.community.exception.LocalUserLoginException;
import com.community.jian.community.exception.ServiceException;
import com.community.jian.community.exception.UserErrorMessage;
import com.community.jian.community.mapper.UserMapper;
import com.community.jian.community.model.User;
import com.community.jian.community.model.UserExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.DefaultEditorKit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceIMP implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public int insertUser(User user) {

        return userMapper.insertSelective(user);
    }

    @Override
    public boolean isUser(String accountId) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(accountId);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() != 0) {
            return true;
        }
        return false;
    }


    public User findUserByAccountID(String accountID) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(accountID);
        return userMapper.selectByExample(userExample).get(0);
    }

    @Override
    public int updateUser(User user) {
        UserExample userExample = new UserExample();
        userExample.or().andPhoneNumEqualTo(user.getPhoneNum());
        return userMapper.updateByExample(user, userExample);
    }

    @Override
    public User findUserByToken(String token) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andTokenEqualTo(token);
        List<User> users = userMapper.selectByExample(userExample);
        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public User login(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());

        List<User> users = userMapper.selectByExample(userExample);
        User pojo = users.size() > 0 ? users.get(0) : null;
        if (null != pojo) {
            //存在用户则更新用户和token
            pojo.setBio(user.getBio());
            pojo.setToken(UUID.randomUUID().toString());
            pojo.setName(user.getName());
            pojo.setIconUrl(user.getIconUrl());
            pojo.setGmtModified(System.currentTimeMillis());

            userMapper.updateByPrimaryKey(pojo);

            pojo = userMapper.selectByPrimaryKey(pojo.getId());
            return pojo;
        } else {
            //不存在用户则注册到数据库
            pojo = new User();
            BeanUtils.copyProperties(user, pojo);
            pojo.setToken(UUID.randomUUID().toString());
            pojo.setGmtCreate(System.currentTimeMillis());
            pojo.setGmtModified(pojo.getGmtCreate());
            userMapper.insertSelective(pojo);
            userExample = new UserExample();
            userExample.createCriteria()
                    .andAccountIdEqualTo(pojo.getAccountId());
            users = userMapper.selectByExample(userExample);
            return users.size() > 0 ? users.get(0) : null;
        }

    }

    @Override
    public boolean validateOnlyPhoneNum(String phoneNum) {
        UserExample example = new UserExample();
        example.or().andPhoneNumEqualTo(phoneNum);
        List<User> users = userMapper.selectByExample(example);
        if (users!=null&&users.size()==0){
            return true;
        }
        return false;
    }

    @Override
    public User registerUser(UserDTO userDTO) {
        User user=new User();
        BeanUtils.copyProperties(userDTO,user);
        user.setToken(UUID.randomUUID().toString());
        user.setIconUrl("/images/register.jpg");
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(System.currentTimeMillis());
        userMapper.insertSelective(user);
        UserExample example = new UserExample();
        example.or().andPhoneNumEqualTo(user.getPhoneNum());
        List<User> users = userMapper.selectByExample(example);
        if (users!=null&&users.size()==1){
            return users.get(0);
        }else {
            return null;
        }


    }

    @Override
    public int updateGithubUser(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        return userMapper.updateByExample(user, userExample);
    }

    @Override
    public User FindUSerByPhoneNum(String phoneNum) {
        UserExample example = new UserExample();
        example.or().andPhoneNumEqualTo(phoneNum);
        List<User> users = userMapper.selectByExample(example);
        if (users!=null&&users.size()==1){
            return users.get(0);
        }
        return null;
    }
}
