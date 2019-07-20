package com.community.jian.community.service;

import com.community.jian.community.mapper.UserMapping;
import com.community.jian.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceIMP implements UserService{
@Autowired
   private UserMapping userMapping;
    @Override
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
    public int updateUser(User user) {
        return userMapping.updateUser(user);
    }

    @Override
    public User findUserByToken(String token) {
        return userMapping.findUserByToken(token);
    }
}
