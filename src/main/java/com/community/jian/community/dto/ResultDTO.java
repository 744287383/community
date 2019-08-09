package com.community.jian.community.dto;

import com.community.jian.community.exception.CommentException;
import com.community.jian.community.exception.ICutomizeMessage;
import lombok.Data;

@Data
public class ResultDTO <T> {
private Integer code;
private String message;
private T data;


    public static ResultDTO successOf(){
    ResultDTO resultDTO=new ResultDTO();
    resultDTO.setCode(200);
    resultDTO.setMessage("请求成功");
    return resultDTO;
}
    public static  <T> ResultDTO successOf(T data){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(data);
        return resultDTO;
    }

public static ResultDTO errorOf(Integer code,String message){
    ResultDTO resultDTO=new ResultDTO();
    resultDTO.setCode(code);
    resultDTO.setMessage(message);
    return resultDTO;
}
    public static ResultDTO errorOf(ICutomizeMessage iCutomizeMessage){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(iCutomizeMessage.getCode());
        resultDTO.setMessage(iCutomizeMessage.getMessage());
        return resultDTO;
    }

    public static ResultDTO errorOf(CommentException e) {
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(e.getCode());
        resultDTO.setMessage(e.getMessage());
        return resultDTO;
    }


}
