#include <gtest/gtest.h>

#include "../src/function_shutil_rmtree.cpp"

class TestShutilRmtree : public ::testing::Test {
protected:
    DirectoryDeleter deleter;
    std::string test_dir = "test_directory";

    void SetUp() override {
        create_test_directory(test_dir);
    }

    void TearDown() override {
        if (std::filesystem::exists(test_dir)) {
            std::filesystem::remove_all(test_dir);
        }
    }

    void create_test_directory(const std::string& directory_path) {
        std::filesystem::create_directories(directory_path);
        
        std::ofstream file1(directory_path + "/file1.txt");
        file1 << "This is a test file.";
        file1.close();

        std::string sub_dir = directory_path + "/subdir";
        std::filesystem::create_directories(sub_dir);
        
        std::ofstream file2(sub_dir + "/file2.txt");
        file2 << "This is another test file.";
        file2.close();
    }
};

TEST_F(TestShutilRmtree, TestDeleteExistingDirectory) {
    std::string result = deleter.delete_directory_tree(test_dir);
    EXPECT_TRUE(result.find("deleted successfully") != std::string::npos);
}

TEST_F(TestShutilRmtree, TestDeleteNonExistentDirectory) {
    std::string non_existent_dir = "non_existent_directory";
    std::string result = deleter.delete_directory_tree(non_existent_dir);
    EXPECT_EQ(result, "Directory '" + non_existent_dir + "' does not exist.");
}

TEST_F(TestShutilRmtree, TestDeleteDirectoryWithFiles) {
    std::string result = deleter.delete_directory_tree(test_dir);
    EXPECT_TRUE(result.find("deleted successfully") != std::string::npos);
}

TEST_F(TestShutilRmtree, TestDeleteWithPermissionsError) {
    #ifdef _WIN32
        // 在 Windows 上创建测试目录和只读文件
        std::string protected_dir = test_dir + "/protected";
        std::filesystem::create_directories(protected_dir);
        std::string protected_file = protected_dir + "/protected_file.txt";
    
        {
            std::ofstream file(protected_file);
            file << "Protected content.";
        }
    
        // 如果文件存在，则将其设置为“只读”（模拟无权限）
        if (std::filesystem::exists(protected_file)) {
            std::string cmd = "attrib +R \"" + protected_file + "\"";
            std::system(cmd.c_str());
        }
    
        // 调用删除目录操作
        std::string result = deleter.delete_directory_tree(protected_dir);
        EXPECT_TRUE(result.find("Failed to delete directory") != std::string::npos)
            << "Result was: " << result;
    
        // 如果文件仍然存在，则将其属性恢复为可写
        if (std::filesystem::exists(protected_file)) {
            std::string cmd = "attrib -R \"" + protected_file + "\"";
            std::system(cmd.c_str());
        }
    #else
        GTEST_SKIP() << "Skipping Windows-specific permissions test on non-Windows OS.";
    #endif
    }
    
    TEST_F(TestShutilRmtree, TestPartialDeletion) {
    #ifdef _WIN32
        // 创建只读文件
        std::string protected_file = test_dir + "/protected_file.txt";
        {
            std::ofstream file(protected_file);
            file << "Partial deletion test.";
        }
    
        // 设置为只读
        if (std::filesystem::exists(protected_file)) {
            std::string cmd = "attrib +R \"" + protected_file + "\"";
            std::system(cmd.c_str());
        }
    
        // 调用删除目录操作
        std::string result = deleter.delete_directory_tree(test_dir);
        EXPECT_TRUE(result.find("Failed to delete directory") != std::string::npos)
            << "Result was: " << result;
    
        // 如果文件仍然存在，则恢复其可写属性，方便后续清理
        if (std::filesystem::exists(protected_file)) {
            std::string cmd = "attrib -R \"" + protected_file + "\"";
            std::system(cmd.c_str());
        }
    #else
        GTEST_SKIP() << "Skipping Windows-specific permissions test on non-Windows OS.";
    #endif
    }
int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}