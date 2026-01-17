#include <cpprest/http_client.h>
#include <cpprest/json.h>
#include <chrono>
#include <thread>

class RequestHandler {
private:
    web::http::client::http_client client;
    web::json::value jsonValue;

public:
    RequestHandler() : client(U("")) {
        client = web::http::client::http_client(U(""));
    }

    std::string processRequest(const std::string& url) {
        web::http::http_request request(web::http::methods::GET);
        request.set_request_uri(utility::conversions::to_string_t(url));

        try {
            auto response = client.request(request).get();
            int statusCode = response.status_code();

            if (statusCode == 200) {
                auto body = response.extract_string().get();

                if (body.empty()) {
                    return "non-json";
                }

                try {
                    jsonValue = web::json::value::parse(body);
                    return "success";
                } catch (const std::exception& e) {
                    return "non-json";
                }
            } else if (statusCode >= 300 && statusCode < 400) {
                return "redirect " + std::to_string(statusCode);
            } else {
                return "error " + std::to_string(statusCode);
            }
        } catch (const std::exception& e) {
            return "timeout";
        }
    }
};

std::string handle_request(const std::string& url) {
    RequestHandler handler;
    return handler.processRequest(url);
}