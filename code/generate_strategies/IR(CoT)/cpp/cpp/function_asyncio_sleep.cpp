
#include <string>
#include <boost/asio.hpp>
#include <chrono>
#include <thread>
class AsyncTaskHandler {
public:
    std::string run_async_task(const std::string& name, float delay){
        boost::asio::io_context io_context;
        boost::asio::steady_timer timer(io_context, 
            boost::asio::chrono::milliseconds(static_cast<int>(delay * 1000)));
        
        timer.wait();
        
        return "Task " + name + " completed after " + std::to_string(delay) + " seconds";
    }
};