
#include <string>
#include <boost/asio.hpp>
#include <chrono>
#include <thread>
#include <stdexcept>
class AsyncTaskHandler {
public:
    std::string run_task(const std::string& name, int delay) {
        if (delay < 0) {
            throw std::invalid_argument("Delay must be a non-negative integer");
        }
    
        boost::asio::io_context io_context;
        boost::asio::steady_timer timer(io_context, boost::asio::chrono::seconds(delay));
        
        timer.wait();
        
        return "Task " + name + " completed after " + std::to_string(delay) + " seconds";
    }
};