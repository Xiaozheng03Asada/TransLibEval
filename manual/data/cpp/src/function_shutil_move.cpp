#include <boost/filesystem.hpp>
#include <boost/filesystem/fstream.hpp>
#include <stdexcept>
#include <string>
#include <fstream>

class FileMover {
public:
    std::string perform_task(const std::string& task, const std::string& args = "") {
        using namespace boost::filesystem;

        if (task == "move") {
            auto pos = args.find(",");
            std::string src = args.substr(0, pos);
            std::string dest = args.substr(pos + 1);

            if (!exists(src)) {
                throw std::runtime_error("Source path '" + src + "' does not exist");
            }

            path src_path(src);
            path dest_path(dest);

            // 检查源路径和目标路径是否相同
            if (equivalent(src_path, dest_path)) {
                return src; // 如果是同一个位置，直接返回源路径
            }

            if (!exists(dest_path)) {
                create_directories(dest_path);
            }

            // 如果目标是目录，则在目标目录下创建同名文件
            if (is_directory(dest_path)) {
                dest_path = dest_path / src_path.filename();
            }

            rename(src_path, dest_path);
            return dest_path.string();
        }else if (task == "create_file") {
            auto pos = args.find(",");
            std::string file_path = args.substr(0, pos);
            std::string content = args.substr(pos + 1);
            std::ofstream file(file_path);
            file << content;
            file.close();
        } else if (task == "create_directory") {
            create_directories(args);
        } else if (task == "path_exists") {
            return exists(args) ? "True" : "False";
        } else if (task == "remove_path") {
            if (is_directory(args)) {
                remove_all(args);
            } else if (is_regular_file(args)) {
                remove(args);
            }
        } else {
            throw std::invalid_argument("Unknown task '" + task + "' specified.");
        }
        return "";
    }
};