#include <string>
#include <curl/curl.h>

class RequestHandler {
public:
    std::string handle_request(const std::string& url) {
        CURL* curl = curl_easy_init();
        if (!curl) {
            return "error";
        }

        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_NOBODY, 1L);
        curl_easy_setopt(curl, CURLOPT_TIMEOUT, 5L);
        curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 0L);

        CURLcode res = curl_easy_perform(curl);

        if (res == CURLE_OPERATION_TIMEDOUT) {
            curl_easy_cleanup(curl);
            return "timeout";
        }

        long response_code;
        curl_easy_getinfo(curl, CURLINFO_RESPONSE_CODE, &response_code);
        curl_easy_cleanup(curl);

        if (response_code == 200) {
            return "success";
        } else if (response_code >= 300 && response_code < 400) {
            return "redirect " + std::to_string(response_code);
        } else {
            return "error " + std::to_string(response_code);
        }
    }
};