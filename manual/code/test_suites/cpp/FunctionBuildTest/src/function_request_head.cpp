#include <cpprest/http_client.h>
#include <cpprest/filestream.h>
#include <stdexcept>

using namespace web::http;
using namespace web::http::client;

std::string handle_request(const std::string& url) {
    http_client_config config;
    config.set_connect_timeout(std::chrono::milliseconds(15000));
    config.set_timeout(std::chrono::milliseconds(15000));
    config.set_redirects_enabled(false);

    http_client client(url, config);

    try {
        http_request request(methods::HEAD);
        http_response response = client.request(request).get();
        int statusCode = response.status_code();

        if (statusCode == 200) {
            return "success";
        } else if (statusCode >= 300 && statusCode < 400) {
            return "redirect " + std::to_string(statusCode);
        } else {
            return "error " + std::to_string(statusCode);
        }
    } catch (const std::exception& e) {
        std::string message = e.what();
        if (message.find("timed out") != std::string::npos || message.find("Connection reset") != std::string::npos) {
            return "timeout";
        }
        throw std::runtime_error(e.what());
    }
}