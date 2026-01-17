#include <gtest/gtest.h>
#include "../src/function_shutil_copytree.cpp"

class TestShutilCopyTree : public ::testing::Test {
protected:
    DirectoryHandler handler;
    std::string src_dir = "test_src_dir";
    std::string dest_dir = "test_dest_dir";

    void SetUp() override {
        boost::filesystem::create_directory(src_dir);
        std::ofstream file1(src_dir + "/file1.txt");
        file1 << "This is file 1.";
        file1.close();

        std::ofstream file2(src_dir + "/file2.txt");
        file2 << "This is file 2.";
        file2.close();

        boost::filesystem::create_directory(src_dir + "/subdir");
        std::ofstream file3(src_dir + "/subdir/file3.txt");
        file3 << "This is file 3 in subdir.";
        file3.close();
    }

    void TearDown() override {
        boost::filesystem::remove_all(src_dir);
        boost::filesystem::remove_all(dest_dir);
    }
};

TEST_F(TestShutilCopyTree, TestCopyDirectory) {
    std::string result = handler.handle_directory_operations(src_dir, dest_dir, "copy");
    EXPECT_EQ(result, "Directory copied from " + src_dir + " to " + dest_dir + ".");
}

TEST_F(TestShutilCopyTree, TestCopyDirectoryExistsError) {
    boost::filesystem::create_directory(dest_dir);
    EXPECT_THROW(handler.handle_directory_operations(src_dir, dest_dir, "copy"), std::runtime_error);
}

TEST_F(TestShutilCopyTree, TestCopyDirectorySourceNotFound) {
    std::string non_existing_dir = "non_existing_dir";
    EXPECT_THROW(handler.handle_directory_operations(non_existing_dir, dest_dir, "copy"), boost::filesystem::filesystem_error);
}

TEST_F(TestShutilCopyTree, TestCopyDirectoryWithPermissions) {
    std::string result = handler.handle_directory_operations(src_dir, dest_dir, "copy");
    EXPECT_EQ(result, "Directory copied from " + src_dir + " to " + dest_dir + ".");
}

TEST_F(TestShutilCopyTree, TestCopyDirectoryWithHiddenFiles) {
    std::ofstream hidden_file(src_dir + "/.hidden_file.txt");
    hidden_file << "This is a hidden file.";
    hidden_file.close();
    std::string result = handler.handle_directory_operations(src_dir, dest_dir, "copy");
    EXPECT_EQ(result, "Directory copied from " + src_dir + " to " + dest_dir + ".");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}