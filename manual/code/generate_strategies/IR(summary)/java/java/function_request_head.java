package com.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RequestHandler {
    public String handle_request(String url) {
        class RequestHandler {
            public String handle_request(String requestUrl) {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .followRedirects(false)
                        .build();

                Request request = new Request.Builder()
                        .url(requestUrl)
                        .head()
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    int statusCode = response.code();
                    response.close();

                    if (statusCode == 200) {
                        return "success";
                    } else if (statusCode >= 300 && statusCode < 400) {
                        return "redirect " + statusCode;
                    } else {
                        return "error " + statusCode;
                    }
                } catch (IOException e) {
                    return "timeout";
                }
            }
        }
        return new RequestHandler().handle_request(url);
    }
}