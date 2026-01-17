
#include <string>
#include <boost/any.hpp>
#include <boost/circular_buffer.hpp>
#include <stdexcept>
class DequeOperations {
public:
    std::string perform_operation(const boost::any& operation_type){
        try {
            const std::string& op = boost::any_cast<std::string>(operation_type);
            boost::circular_buffer<int> d(10);  
    
            if (op == "append_and_appendleft") {
                d.push_back(1);
                d.push_front(0);
                return std::to_string(d.front()) + ", " + std::to_string(d[1]);
            }
            else if (op == "pop_and_popleft") {
                d.push_back(1);
                d.push_back(2);
                d.push_back(3);
                d.pop_front();
                d.pop_back();
                return std::to_string(d.size());
            }
            else if (op == "remove") {
                d.push_back(1);
                d.push_back(2);
                d.push_back(3);
                
                for (auto it = d.begin(); it != d.end(); ++it) {
                    if (*it == 2) {
                        d.erase(it);
                        break;
                    }
                }
                return std::to_string(d.front()) + ", " + std::to_string(d.back());
            }
            else if (op == "clear") {
                d.push_back(1);
                d.push_back(2);
                d.push_back(3);
                d.clear();
                return std::to_string(d.size());
            }
            else {
                throw std::invalid_argument("Invalid operation type");
            }
        }
        catch (const boost::bad_any_cast&) {
            throw std::invalid_argument("Input should be a string");
        }
    }
};
