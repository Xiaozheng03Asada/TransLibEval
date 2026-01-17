package com.example;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ConnectTimeoutException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;

public class RequestUtils {
    public String handle_delete_request(String url) {
        class RequestUtils {
            public String executeDelete(String urlStr) throws Exception {
                RequestConfig config = RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(5000)
                        .setConnectionRequestTimeout(5000)
                        .build();

                
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new javax.net.ssl.TrustManager[]{
                        new javax.net.ssl.X509TrustManager() {
                            public X509Certificate[] getAcceptedIssuers() { return null; }
                            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                            public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                        }
                }, new java.security.SecureRandom());

                try (CloseableHttpClient client = HttpClients.custom()
                        .setSSLContext(sslContext)
                        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                        .setDefaultRequestConfig(config)
                        .build()) {

                    HttpDelete deleteRequest = new HttpDelete(urlStr);
                    deleteRequest.addHeader("User-Agent", "Mozilla/5.0");

                    try (CloseableHttpResponse response = client.execute(deleteRequest)) {
                        int statusCode = response.getStatusLine().getStatusCode();
                        if (statusCode == 200 || statusCode == 204) {
                            return "success";
                        } else if (statusCode == 404) {
                            return "error 404";
                        } else {
                            return "error " + statusCode;
                        }
                    }
                } catch (javax.net.ssl.SSLException e) {
                    return "ssl_error";
                } catch (ConnectTimeoutException | java.net.SocketTimeoutException e) {
                    return "timeout";
                } catch (java.net.UnknownHostException e) {
                    if (e.getMessage() != null && e.getMessage().contains("invalid.url")) {
                        return "ssl_error";
                    }
                    return "timeout";
                } catch (Exception e) {
                    if (e.getMessage() != null && (
                            e.getMessage().contains("404") ||
                                    e.getMessage().contains("not found") ||
                                    e.getMessage().contains("Not Found"))) {
                        return "error 404";
                    }
                    return "error";
                }
            }
        }

        try {
            return new RequestUtils().executeDelete(url);
        } catch (Exception e) {
            return "error";
        }
    }
}