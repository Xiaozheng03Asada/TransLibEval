#include <boost/filesystem.hpp>
#include <string>
#include <sstream>
#include <fstream>
#include <filesystem>
#include <cstdlib>  


class DirectoryDeleter {
public:
    std::string delete_directory_tree(const std::string& directory_path) {
        if (!boost::filesystem::exists(directory_path)) {
            return "Directory '" + directory_path + "' does not exist.";
        }

        boost::system::error_code ec;
        if (boost::filesystem::remove_all(directory_path, ec) == static_cast<uintmax_t>(-1)) {
            return "Failed to delete directory '" + directory_path + "': " + ec.message();
        }
        
        return "Directory '" + directory_path + "' deleted successfully.";
    }
};