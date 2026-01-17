package com.example;

import com.google.gson.Gson;
import okhttp3.*;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PostRequestHandler {
    public String handle_post_request(String url, String title, String body, int userId) {
        class PostRequestHandler {
            private final OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .build();
            private final Gson gson = new Gson();

            public String handleRequest(String url, String title, String body, int userId) {
                Map<String, Object> data = new HashMap<>();
                data.put("title", title);
                data.put("body", body);
                data.put("userId", userId);

                String jsonBody = gson.toJson(data);
                RequestBody requestBody = RequestBody.create(jsonBody, MediaType.parse("application/json"));
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if (response.code() == 200 || response.code() == 201) {
                        return "success";
                    } else {
                        return "error " + response.code();
                    }
                } catch (IOException e) {
                    if (e instanceof java.net.SocketTimeoutException) {
                        return "timeout";
                    }
                    return "error";
                }
            }
        }

        PostRequestHandler handler = new PostRequestHandler();
        return handler.handleRequest(url, title, body, userId);
    }
}