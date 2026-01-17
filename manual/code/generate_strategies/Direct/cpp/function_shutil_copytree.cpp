
#include <iostream>
#include <string>
#include <boost/filesystem.hpp>
#include <fstream>
class DirectoryHandler {
public:
    std::string handle_directory_operations(std::string src_dir, std::string dest_dir, std::string operation = "copy") {
        using namespace boost::filesystem;
        if (operation == "copy") {
            if (exists(dest_dir)) {
                throw std::runtime_error("The destination directory '" + dest_dir + "' already exists.");
            }
            boost::filesystem::copy(src_dir, dest_dir);
            return "Directory copied from " + src_dir + " to " + dest_dir + ".";
        } else {
            throw std::runtime_error("Unsupported operation. Only 'copy' is supported.");
        }
    }
};