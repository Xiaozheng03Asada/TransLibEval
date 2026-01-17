#include <string>
#include <boost/any.hpp>
#include <boost/functional/hash.hpp>
#include <sstream>
class HashingExample {
public:
    std::string compute_hash(const boost::any& input_data){
        std::size_t hash_value;
        
        try {
            if (input_data.empty()) {
                hash_value = boost::hash_value("");
            }
            else if (input_data.type() == typeid(std::string)) {
                hash_value = boost::hash_value(boost::any_cast<std::string>(input_data));
            }
            else if (input_data.type() == typeid(int)) {
                hash_value = boost::hash_value(boost::any_cast<int>(input_data));
            }
            else if (input_data.type() == typeid(double)) {
                hash_value = boost::hash_value(boost::any_cast<double>(input_data));
            }
            else if (input_data.type() == typeid(const char*)) {
                hash_value = boost::hash_value(std::string(boost::any_cast<const char*>(input_data)));
            }
            else {
                hash_value = input_data.type().hash_code();
            }
            
            std::stringstream ss;
            ss << hash_value;
            return ss.str();
        }
        catch (...) {
            return "0";
        }
    }
};