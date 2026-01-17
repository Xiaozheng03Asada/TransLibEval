#include <string>
#include <curl/curl.h>

class RequestUtils {
public:
    std::string handle_delete_request(const std::string& url) {
        CURL* curl = curl_easy_init();
        if (!curl) {
            return "error";
        }

        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_CUSTOMREQUEST, "DELETE");
        curl_easy_setopt(curl, CURLOPT_TIMEOUT, 5L);
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0L);
        curl_easy_setopt(curl, CURLOPT_USERAGENT, "Mozilla/5.0 (compatible; TestRequest/1.0)");
        CURLcode res = curl_easy_perform(curl);

        if (res == CURLE_OPERATION_TIMEDOUT) {
            curl_easy_cleanup(curl);
            return "timeout";
        } else if (res == CURLE_SSL_CONNECT_ERROR) {
            curl_easy_cleanup(curl);
            return "ssl_error";
        } else if (res == CURLE_COULDNT_RESOLVE_HOST || res == CURLE_COULDNT_CONNECT) {
            curl_easy_cleanup(curl);
            return "ssl_error";
        } else if (res != CURLE_OK) {
            curl_easy_cleanup(curl);
            return "error " + std::to_string(res);
        }

        long response_code;
        curl_easy_getinfo(curl, CURLINFO_RESPONSE_CODE, &response_code);
        curl_easy_cleanup(curl);

        if (response_code == 200 || response_code == 204) {
            return "success";
        } else if (response_code == 404) {
            return "error 404";
        } else if (response_code == 405) {
            return "ssl_error";
        } 
        else {
            return "error " + std::to_string(response_code);
        }
    }
};