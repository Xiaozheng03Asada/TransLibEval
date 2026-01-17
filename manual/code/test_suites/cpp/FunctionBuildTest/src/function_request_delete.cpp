#include <iostream>
#include <string>
#include <curl/curl.h>
#include <openssl/ssl.h>
#include <openssl/x509.h>
#include <openssl/err.h>

class RequestUtils {
public:
    std::string handle_delete_request(const std::string& url) {
        CURL* curl = curl_easy_init();
        if (curl) {
            curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
            curl_easy_setopt(curl, CURLOPT_CUSTOMREQUEST, "DELETE");
            curl_easy_setopt(curl, CURLOPT_USERAGENT, "Mozilla/5.0");
            curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0L);
            curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, 0L);
            curl_easy_setopt(curl, CURLOPT_CONNECTTIMEOUT, 5L);
            curl_easy_setopt(curl, CURLOPT_TIMEOUT, 5L);

            CURLcode res = curl_easy_perform(curl);
            long statusCode;
            curl_easy_getinfo(curl, CURLINFO_RESPONSE_CODE, &statusCode);

            curl_easy_cleanup(curl);

            if (res == CURLE_OK) {
                if (statusCode == 200 || statusCode == 204) {
                    return "success";
                } else if (statusCode == 404) {
                    return "error 404";
                } else {
                    return "error " + std::to_string(statusCode);
                }
            } else {
                if (res == CURLE_SSL_CONNECT_ERROR) {
                    return "ssl_error";
                } else if (res == CURLE_OPERATION_TIMEDOUT) {
                    return "timeout";
                } else if (res == CURLE_COULDNT_RESOLVE_HOST) {
                    return "timeout";
                } else {
                    return "error";
                }
            }
        }
        return "error";
    }
};