package com.community.jian.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.*;
import cn.ucloud.ufile.auth.sign.UfileSignatureException;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileParamException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import com.community.jian.community.exception.UploadFileMessage;
import com.community.jian.community.exception.UploadImgException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Component
public class UCloudProvider {
    @Value("${UCloudProvider.public_key}")
    private String public_key;
    @Value("${UCloudProvider.private_key}")
    private String private_key;
    @Value("${UCloudProvider.service_city}")
    private String service_city;
    @Value("${UCloudProvider.suffix}")
    private String suffix ;
    @Value("${UCloudProvider.expiryTime}")
    private Integer expiryTime;

    public String uploadImg(MultipartFile file) {
        if (file == null) {
            throw new UploadImgException(UploadFileMessage.FILE_NOT_NULL);
        }
        ObjectAuthorization OBJECT_AUTHORIZER = new UfileObjectLocalAuthorization(public_key, private_key);
        ObjectConfig config = new ObjectConfig(service_city, "ufileos.com");

        String fileName = fileNameHandle(file);
        PutObjectResultBean response=null;
        response = getPutObjectResultBean(file, OBJECT_AUTHORIZER, config, fileName);
         String browseURL = getBrowseURL(OBJECT_AUTHORIZER, config, fileName, response);
        return browseURL;
    }

    @Nullable
    private String getBrowseURL(ObjectAuthorization OBJECT_AUTHORIZER, ObjectConfig config, String fileName, PutObjectResultBean response) {
        if (response != null && response.getRetCode() == 0) {
            String url = null;
            try {
                url = UfileClient.object(OBJECT_AUTHORIZER, config)
                        .getDownloadUrlFromPrivateBucket(fileName, suffix, expiryTime).createUrl();
            } catch (UfileParamException e) {
                e.printStackTrace();
                throw new UploadImgException(UploadFileMessage.UNKNOWN_ERROR);
            } catch (UfileAuthorizationException e) {
                e.printStackTrace();
                throw new UploadImgException(UploadFileMessage.UNKNOWN_ERROR);
            } catch (UfileSignatureException e) {
                e.printStackTrace();
                throw new UploadImgException(UploadFileMessage.UNKNOWN_ERROR);
            }
            return url;
        } else {
            throw new UploadImgException(UploadFileMessage.UPLOAD_FILE_ERROR);
        }
    }

    private PutObjectResultBean getPutObjectResultBean(MultipartFile file, ObjectAuthorization OBJECT_AUTHORIZER, ObjectConfig config, String fileName) {
        PutObjectResultBean response;
        try {

             response = UfileClient.object(OBJECT_AUTHORIZER, config)
                    .putObject(file.getInputStream(), file.getContentType())
                    .nameAs(fileName)
                    .toBucket(suffix)
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(long bytesWritten, long contentLength) {

                        }
                    })
                    .execute();


        } catch (UfileClientException e) {
            e.printStackTrace();
            throw new UploadImgException(UploadFileMessage.UNKNOWN_ERROR);
        } catch (UfileServerException e) {
            e.printStackTrace();
            throw new UploadImgException(UploadFileMessage.UNKNOWN_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UploadImgException(UploadFileMessage.UNKNOWN_ERROR);
        }
        return response;
    }

    @NotNull
    private String fileNameHandle(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String[] cutName = fileName.split("\\.");
        if (cutName != null && cutName.length > 1) {
            fileName = UUID.randomUUID().toString() + "." + cutName[cutName.length - 1];
        } else {
            throw new UploadImgException(UploadFileMessage.NOT_SUPPORT_FILE_TYPE);
        }
        return fileName;
    }
}
