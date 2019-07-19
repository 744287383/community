package com.community.jian.community.provider;

import com.alibaba.fastjson.JSON;
import com.community.jian.community.dto.AccessTokenDTO;
import com.community.jian.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class GithubProvider {
public String getAccessToken(AccessTokenDTO accessTokenDTO){

MediaType json= MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {

            String string = response.body().string();
            System.out.println(string);
            string= string.split("&")[0].split("=")[1];
            System.out.println(string.length());
            return 40==string.length()?string:null;
    } catch (IOException e) {
            e.printStackTrace();
        }

    return null;
}
public GithubUser getUser(String access_token){
    OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+access_token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return JSON.parseObject(response.body().string(),GithubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    return null;
}


}
