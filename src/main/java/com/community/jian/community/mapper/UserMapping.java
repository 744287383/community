package com.community.jian.community.mapper;

import com.community.jian.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapping {
    @Select("select * from user where account_id=#{accountID}")
    User findUserByAccountID(@Param("accountID") String accountID);
    @Insert("insert into user (account_id,name,bio,token) values(#{accountId},#{name},#{bio},#{token})")
    int insertUser(User user);
    @Update("update user set name=#{name},token=#{token},bio=#{bio} where account_id=#{accountId}")
    int updateUser(User user);
    @Select("select * from user where token=#{token}")
    User findUserByToken(@Param("token") String token);

}
