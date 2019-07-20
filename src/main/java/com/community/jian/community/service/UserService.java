package com.community.jian.community.service;

import com.community.jian.community.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
    int insertUser(User user);
    boolean isUser(String AccountId);
    User findUserByAccountID(String accountID);
    int updateUser(User user);
    User findUserByToken( String token);
}
