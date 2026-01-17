#include <string>
#include <boost/asio.hpp>

class EventTaskHandler {
public:
    std::string might_fail_function(const std::string& event_status, const std::string& task){
        boost::asio::io_context io_context;
        
        if (event_status == "triggered") {
            return "Task " + task + " completed";
        }
        return "Task " + task + " is waiting for the event";
    }
};