#include <gtest/gtest.h>
#include <string>
#include <filesystem>
#include "../src/function_Whoosh_index_create_in.cpp"

class TestIndexManager : public ::testing::Test {
protected:
    std::string index_dir = "test_index";
    IndexManager index_manager;

    void SetUp() override {
        cleanupDirectory();
    }

    void TearDown() override {
        cleanupDirectory();
    }

    void cleanupDirectory() {
        if (std::filesystem::exists(index_dir)) {
            std::filesystem::remove_all(index_dir);
        }
    }
};

TEST_F(TestIndexManager, test_create_index) {
    std::string result = index_manager.add_and_search<std::string>(index_dir);
    EXPECT_EQ(result, "No action performed");
    EXPECT_TRUE(std::filesystem::exists(index_dir));
    EXPECT_TRUE(std::filesystem::is_directory(index_dir));
}

TEST_F(TestIndexManager, test_add_document) {
    std::string result = index_manager.add_and_search<std::string>(
        index_dir, "This is a test document.", "");
    EXPECT_EQ(result, "Document added successfully");
    
    result = index_manager.add_and_search<std::string>(
        index_dir, "", "test");
    EXPECT_EQ(result, "This is a test document.");
}

TEST_F(TestIndexManager, test_search_document) {
    index_manager.add_and_search<std::string>(
        index_dir, "This is another test document.", "");
    index_manager.add_and_search<std::string>(
        index_dir, "This is a different document.", "");
        
    std::string result = index_manager.add_and_search<std::string>(
        index_dir, "", "different");
    EXPECT_EQ(result, "This is a different document.");
}

TEST_F(TestIndexManager, test_multiple_documents) {
    index_manager.add_and_search<std::string>(index_dir, "Document one", "");
    index_manager.add_and_search<std::string>(index_dir, "Document two", "");
    index_manager.add_and_search<std::string>(index_dir, "Document three", "");
    
    std::string result = index_manager.add_and_search<std::string>(
        index_dir, "", "Document");
    EXPECT_EQ(result, "Document one Document two Document three");
}

TEST_F(TestIndexManager, test_search_nonexistent_keyword) {
    index_manager.add_and_search<std::string>(
        index_dir, "Some real document content", "");
    std::string result = index_manager.add_and_search<std::string>(
        index_dir, "", "nonexistent keyword");
    EXPECT_EQ(result, "");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}