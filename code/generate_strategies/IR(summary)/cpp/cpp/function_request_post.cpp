#include <string>
#include <curl/curl.h>
#include <sstream>

class PostRequestHandler {
public:
    std::string handle_post_request(const std::string& url, 
                                  const std::string& title, 
                                  const std::string& body, 
                                  int userId) {
        CURL* curl = curl_easy_init();
        if (!curl) {
            return "error";
        }

        
        std::string json_data = "{\"title\":\"" + title + 
                              "\",\"body\":\"" + body + 
                              "\",\"userId\":" + std::to_string(userId) + "}";

        struct curl_slist* headers = nullptr;
        headers = curl_slist_append(headers, "Content-Type: application/json");
        headers = curl_slist_append(headers, "User-Agent: Mozilla/5.0");

        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_POST, 1L);
        curl_easy_setopt(curl, CURLOPT_POSTFIELDS, json_data.c_str());
        curl_easy_setopt(curl, CURLOPT_TIMEOUT, 5L);
        curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);
        curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 0L);

        CURLcode res = curl_easy_perform(curl);

        if (res == CURLE_OPERATION_TIMEDOUT) {
            curl_slist_free_all(headers);
            curl_easy_cleanup(curl);
            return "timeout";
        }

        long response_code;
        curl_easy_getinfo(curl, CURLINFO_RESPONSE_CODE, &response_code);
        
        curl_slist_free_all(headers);
        curl_easy_cleanup(curl);

        if (res != CURLE_OK) {
            return "error " + std::to_string(res);
        }

        if (response_code == 200 || response_code == 201) {
            return "success";
        } else {
            return "error " + std::to_string(response_code);
        }
    }
};