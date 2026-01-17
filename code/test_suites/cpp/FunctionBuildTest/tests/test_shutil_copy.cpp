#include <gtest/gtest.h>
#include "../src/function_shutil_copy.cpp"

class TestShutilCopy : public ::testing::Test {
protected:
    FileHandler handler;
    std::string src_file, dst_file;

    void SetUp() override {
        std::string result = handler.handle_file_operations("Hello, world!", "copy");
        size_t pos = result.find(",");
        src_file = result.substr(0, pos);
        dst_file = result.substr(pos + 1);
    }

    void TearDown() override {
        handler.handle_file_operations(src_file, "remove");
        handler.handle_file_operations(dst_file, "remove");
    }
};

TEST_F(TestShutilCopy, TestCopyFileToDirectory) {
    EXPECT_EQ(handler.handle_file_operations(dst_file, "exists"), "True");
}

TEST_F(TestShutilCopy, TestCopyFileContent) {
    std::string src_content = handler.handle_file_operations(src_file, "read");
    std::string dst_content = handler.handle_file_operations(dst_file, "read");
    EXPECT_EQ(src_content, dst_content);
}

TEST_F(TestShutilCopy, TestCopyFileOverwriteExistingFile) {
    std::string new_content = "New content";
    handler.handle_file_operations(new_content, "copy", dst_file);
    std::string updated_content = handler.handle_file_operations(dst_file, "read");
    EXPECT_EQ(updated_content, new_content);
}

TEST_F(TestShutilCopy, TestCopyFileToCurrentDirectory) {
    std::string current_dir_file = "test_copy.txt";
    std::string result = handler.handle_file_operations("Hello, world!", "copy", current_dir_file);
    size_t pos = result.find(",");
    std::string dst_file = result.substr(pos + 1);
    EXPECT_EQ(handler.handle_file_operations(dst_file, "exists"), "True");
    handler.handle_file_operations(dst_file, "remove");
}

TEST_F(TestShutilCopy, TestCopyNonExistentFile) {
    std::string non_existent_file = "non_existent.txt";
    EXPECT_EQ(handler.handle_file_operations(non_existent_file, "exists"), "False");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}