package com.community.jian.community.service;

import com.community.jian.community.dto.UserDTO;
import com.community.jian.community.model.User;

public interface UserService {
    int insertUser(User user);
    boolean isUser(String AccountId);
    User findUserByAccountID(String accountID);
    int updateUser(User user);
    User findUserByToken( String token);
    User login(User user);

    boolean validateOnlyPhoneNum(String phoneNum);

    User registerUser(UserDTO userDTO);

    int updateGithubUser(User user);

    User FindUSerByPhoneNum(String phoneNum);

}
