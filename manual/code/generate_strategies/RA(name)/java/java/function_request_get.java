package com.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

public class RequestHandler {
    public String handle_request(String url) {
        class RequestHandler {
            private final OkHttpClient client;
            private final ObjectMapper objectMapper;

            RequestHandler() {
                this.client = new OkHttpClient.Builder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(5, TimeUnit.SECONDS)
                        .followRedirects(false)
                        .build();
                this.objectMapper = new ObjectMapper();
            }

            String processRequest(String url) {
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    int statusCode = response.code();

                    if (statusCode == 200) {
                        ResponseBody body = response.body();
                        if (body == null) {
                            return "non-json";
                        }

                        // 先获取响应体的字符串
                        String responseStr = body.string();

                        if (responseStr.trim().isEmpty()) {
                            return "non-json";
                        }

                        try {
                            // 使用保存的字符串进行JSON解析
                            objectMapper.readTree(responseStr);
                            return "success";
                        } catch (Exception e) {
                            return "non-json";
                        }
                    } else if (statusCode >= 300 && statusCode < 400) {
                        return "redirect " + statusCode;
                    } else {
                        return "error " + statusCode;
                    }
                } catch (Exception e) {
                    return "timeout";
                }
            }
        }

        RequestHandler handler = new RequestHandler();
        return handler.processRequest(url);
    }
}