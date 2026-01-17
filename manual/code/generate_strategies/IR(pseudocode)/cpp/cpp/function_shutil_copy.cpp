#include <boost/filesystem.hpp>
#include <boost/filesystem/fstream.hpp>
#include <stdexcept>
#include <string>
#include <fstream>

class FileHandler {
public:
    std::string handle_file_operations(const std::string& content = "Hello, world!", const std::string& operation = "copy", const std::string& target_path = "") {
        using namespace boost::filesystem;

        if (operation == "copy") {
            path temp_file = unique_path();
            std::ofstream(temp_file.string()) << content;

            path target = target_path.empty() ? unique_path() : path(target_path);
            copy_file(temp_file, target, copy_option::overwrite_if_exists);
            return temp_file.string() + "," + target.string();
        } else if (operation == "read") {
            std::ifstream file(content);
            std::stringstream buffer;
            buffer << file.rdbuf();
            return buffer.str();
        } else if (operation == "exists") {
            return exists(content) ? "True" : "False";
        } else if (operation == "remove") {
            if (exists(content)) {
                remove(content);
            }
            return "removed";
        } else {
            throw std::invalid_argument("Unsupported operation. Choose from 'copy', 'read', 'exists', 'remove'.");
        }
    }
};