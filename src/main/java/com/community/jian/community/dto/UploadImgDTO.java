package com.community.jian.community.dto;

import com.community.jian.community.exception.UploadFileMessage;
import com.community.jian.community.exception.UploadImgException;
import lombok.Data;

@Data
public class UploadImgDTO {
  private  Integer success;
  private String message;
  private String url;

  public static  UploadImgDTO successOf(String url){
      UploadImgDTO uploadImgDTO=new UploadImgDTO();
      uploadImgDTO.setSuccess(1);
      uploadImgDTO.setMessage("上传成功");
      uploadImgDTO.setUrl(url);
      return uploadImgDTO;
  }
    public static  UploadImgDTO errorOf(String url){
        UploadImgDTO uploadImgDTO=new UploadImgDTO();
        uploadImgDTO.setSuccess(0);
        uploadImgDTO.setMessage("上传失败");
        uploadImgDTO.setUrl(null);
        return uploadImgDTO;
    }



    public static UploadImgDTO errorOf(UploadImgException e) {
        UploadImgDTO uploadImgDTO=new UploadImgDTO();
        uploadImgDTO.setSuccess(e.getCode());
        uploadImgDTO.setMessage(e.getMessage());
        return uploadImgDTO;
    }
}
