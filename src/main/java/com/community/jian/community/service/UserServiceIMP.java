package com.community.jian.community.service;

import com.community.jian.community.mapper.UserMapping;
import com.community.jian.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceIMP implements UserService{
@Autowired
   private UserMapping userMapping;
    @Override
    @Transactional
    public int insertUser(User user) {

        return userMapping.insertUser(user);
    }

    @Override
    public boolean isUser(String accountId) {
        User user=userMapping.findUserByAccountID(accountId);
        if (null!=user) {
            return true;
        }
        return false;
    }


    public User findUserByAccountID(String accountID) {
        return userMapping.findUserByAccountID(accountID);
    }

    @Override
    @Transactional
    public int updateUser(User user) {
        return userMapping.updateUser(user);
    }

    @Override
    public User findUserByToken(String token) {
        return userMapping.findUserByToken(token);
    }

    @Override
    public User login(User user) {
        User pojo = userMapping.findUserByAccountID(user.getAccountId());
        if (null != pojo) {
            //存在用户则更新用户和token
            pojo.setBio(user.getBio());
            pojo.setToken(UUID.randomUUID().toString());
            pojo.setName(user.getName());
            pojo.setIconUrl(user.getIconUrl());
            pojo.setGmtModified(System.currentTimeMillis());
            userMapping.updateUser(pojo);
            pojo =userMapping.findUserByAccountID(user.getAccountId());
            return pojo;
        } else {
            //不存在用户则注册到数据库
            pojo = new User();
            BeanUtils.copyProperties(user,pojo);
            pojo.setToken(UUID.randomUUID().toString());
            pojo.setGmtCreate(System.currentTimeMillis());
            pojo.setGmtModified(pojo.getGmtCreate());
            userMapping.insertUser(user);
            pojo =userMapping.findUserByAccountID(user.getAccountId());
            return pojo;
        }

    }
}
