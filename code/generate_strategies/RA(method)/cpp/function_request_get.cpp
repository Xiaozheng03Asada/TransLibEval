#include <curl/curl.h>
#include <string>
#include <sstream>
#include <stdexcept>

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
        curl_easy_setopt(curl, CURLOPT_NOSIGNAL, 1L);      
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0L); 

        CURLcode res = curl_easy_perform(curl);

        long response_code = 0;
        curl_easy_getinfo(curl, CURLINFO_RESPONSE_CODE, &response_code);
        curl_easy_cleanup(curl);

        if (res == CURLE_OPERATION_TIMEDOUT) {
            return "timeout";
        } else if (res == CURLE_SSL_CONNECT_ERROR || res == CURLE_COULDNT_RESOLVE_HOST) {
            return "ssl_error";
        } else if (res != CURLE_OK) {
            return "error";
        }

        if (response_code == 200) {
            return "success";
        } else if (response_code >= 300 && response_code < 400) {
            return "redirect " + std::to_string(response_code);
        } else {
            return "error " + std::to_string(response_code);
        }
    }
};