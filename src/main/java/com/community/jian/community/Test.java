package com.community.jian.community;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Test {
    OkHttpClient client = new OkHttpClient();

      String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            return string;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            new Thread() {
                @Override
                public void run() {

                    try {
                        new Test().run("http://localhost:8080/count?id=513");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
      }
}
