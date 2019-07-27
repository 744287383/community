package com.community.jian.community.mapper;

import com.community.jian.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapping {
    @Select("select * from user where account_id=#{accountID}")
    User findUserByAccountID(@Param("accountID") String accountID);
    @Insert("insert into user (account_id,name,bio,token,icon_url,gmt_create,gmt_modified) values(#{accountId},#{name},#{bio},#{token},#{iconUrl},#{gmtCreate},#{gmtModified})")
    int insertUser(User user);
    @Update("update user set name=#{name},token=#{token},bio=#{bio},icon_url=#{iconUrl},gmt_modified=#{gmtModified} where account_id=#{accountId}")
    int updateUser(User user);
    @Select("select * from user where token=#{token}")
    User findUserByToken(@Param("token") String token);
    @Select("select * from user where id=#{id}")
    User findUserById(@Param("id") String id);

}
