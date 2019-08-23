package com.community.jian.community.Controller;

import com.community.jian.community.dto.UploadImgDTO;
import com.community.jian.community.provider.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadFileController {
    @Autowired
    private UCloudProvider uCloudProvider;
@RequestMapping("/uploadImg")
@ResponseBody
    public UploadImgDTO uploadImg(@RequestParam("editormd-image-file") MultipartFile file){
    String url = uCloudProvider.uploadImg(file);
    return UploadImgDTO.successOf(url);
}
}
