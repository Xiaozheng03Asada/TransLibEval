#include <iostream>
#include <string>
#include <map>
#include <curl/curl.h>
#include <json/json.h>

class PostRequestHandler {
public:
    std::string handle_post_request(const std::string& url, const std::string& title, const std::string& body, int userId) {
        CURL* curl = curl_easy_init();
        if (curl) {
            std::map<std::string, Json::Value> data;
            data["title"] = title;
            data["body"] = body;
            data["userId"] = userId;

            Json::Value jsonData;
            for (const auto& pair : data) {
                jsonData[pair.first] = pair.second;
            }

            Json::StreamWriterBuilder writer;
            std::string jsonBody = Json::writeString(writer, jsonData);

            struct curl_slist* headers = NULL;
            headers = curl_slist_append(headers, "Content-Type: application/json");

            curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
            curl_easy_setopt(curl, CURLOPT_POSTFIELDS, jsonBody.c_str());
            curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);

            CURLcode res = curl_easy_perform(curl);
            long response_code;
            curl_easy_getinfo(curl, CURLINFO_RESPONSE_CODE, &response_code);

            curl_slist_free_all(headers);
            curl_easy_cleanup(curl);

            if (res == CURLE_OK && (response_code == 200 || response_code == 201)) {
                return "success";
            } else if (res == CURLE_OPERATION_TIMEDOUT) {
                return "timeout";
            } else {
                return "error " + std::to_string(response_code);
            }
        }
        return "error";
    }
};