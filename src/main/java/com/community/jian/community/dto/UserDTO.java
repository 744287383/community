package com.community.jian.community.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDTO {
    private Long id;

    private String accountId;
    @NotBlank(message = "昵称不能为空")
    @Size(min = 2,max = 100,message = "请输入不少于2个字符的昵称")
    private String name;
    @NotBlank(message = "个性签名不能为空")
    @Size(min = 6,max = 20,message = "请输入6到20为的字符密码")
    private String bio;

    private String token;

    private String iconUrl;

    private Long gmtCreate;

    private Long gmtModified;
    @NotBlank(message = "密码不能为空")
    @Size(min = 6,max = 20,message = "请输入6到20为的字符密码")
    private String password;
    @NotBlank(message = "手机号码不能为空")
    @Size(min = 11,max = 11,message = "请输入正确的11位手机号")
    private String phoneNum;

}