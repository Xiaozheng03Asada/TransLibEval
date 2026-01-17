#include <gtest/gtest.h>
#include "../src/function_shutil_move.cpp"

class TestShutilMove : public ::testing::Test {
protected:   
    FileMover mover;
    std::string src_file = "test_file.txt";
    std::string src_dir = "test_dir";
    std::string dest_dir = "dest_dir";

    void SetUp() override {
        mover.perform_task("create_file", src_file + ",This is a test file.");
        mover.perform_task("create_directory", src_dir);
        mover.perform_task("create_file", src_dir + "/nested_file.txt,This is a file in a directory.");
        mover.perform_task("create_directory", dest_dir);
    }

    void TearDown() override {
        for (const auto& path : {src_file, src_dir, dest_dir}) {
            if (mover.perform_task("path_exists", path) == "True") {
                mover.perform_task("remove_path", path);
            }
        }
    }
};

TEST_F(TestShutilMove, TestMoveFile) {
    std::string moved_path = mover.perform_task("move", src_file + "," + dest_dir);
    EXPECT_EQ(mover.perform_task("path_exists", dest_dir + "/test_file.txt"), "True");
    EXPECT_EQ(mover.perform_task("path_exists", src_file), "False");
}

TEST_F(TestShutilMove, TestMoveNonexistentFile) {
    EXPECT_THROW(mover.perform_task("move", "nonexistent.txt,dest_dir"), std::runtime_error);
}

TEST_F(TestShutilMove, TestMoveFileToSameLocation) {
    std::string moved_path = mover.perform_task("move", src_file + "," + src_file);
    EXPECT_EQ(mover.perform_task("path_exists", src_file), "True");
    EXPECT_EQ(moved_path, src_file);
}

TEST_F(TestShutilMove, TestMoveToNonexistentDirectory) {
    std::string non_existing_dest = "non_existing_dest_dir";
    if (mover.perform_task("path_exists", non_existing_dest) == "True") {
        mover.perform_task("remove_path", non_existing_dest);
    }
    std::string moved_path = mover.perform_task("move", src_dir + "," + non_existing_dest);
    EXPECT_EQ(mover.perform_task("path_exists", non_existing_dest), "True");
    EXPECT_EQ(mover.perform_task("path_exists", src_dir), "False");

    mover.perform_task("remove_path", non_existing_dest);
}

TEST_F(TestShutilMove, TestMoveLargeFile) {
    std::string large_file_path = src_dir + "/large_file.txt";
    std::string large_content(10 * 1024 * 1024, '\0'); // 10 MB file
    mover.perform_task("create_file", large_file_path + "," + large_content);
    mover.perform_task("move", large_file_path + "," + dest_dir);
    EXPECT_EQ(mover.perform_task("path_exists", dest_dir + "/large_file.txt"), "True");
    EXPECT_EQ(mover.perform_task("path_exists", large_file_path), "False");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}