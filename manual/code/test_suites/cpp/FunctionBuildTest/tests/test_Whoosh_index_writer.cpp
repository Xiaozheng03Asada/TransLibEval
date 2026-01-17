#include <gtest/gtest.h>
#include <string>
#include <filesystem>
#include "../src/function_Whoosh_index_writer.cpp"

class TestManageSearchDocuments : public ::testing::Test {
protected:
    std::string index_dir = "test_index";
    WhooshIndexManager manager;

    void SetUp() override {
        cleanup();
        initialize_index(index_dir);
    }

    void TearDown() override {
        cleanup();
    }

    void cleanup() {
        if (std::filesystem::exists(index_dir)) {
            std::filesystem::remove_all(index_dir);
        }
    }

    void initialize_index(const std::string& index_dir) {
        manager.manage_and_search_documents<std::string>(index_dir, "Document 1: Test text.");
    }
};

TEST_F(TestManageSearchDocuments, test_add_document) {
    std::string result = manager.manage_and_search_documents<std::string>(
        index_dir, "Document 2: Another test text.", "Document");
    EXPECT_TRUE(result.find("Document 2: Another test text.") != std::string::npos);
}

TEST_F(TestManageSearchDocuments, test_empty_index_search) {
    std::string result = manager.manage_and_search_documents<std::string>(
        index_dir, "", "Nonexistent");
    EXPECT_EQ(result, "No documents found");
}

TEST_F(TestManageSearchDocuments, test_empty_index) {
    std::string result = manager.manage_and_search_documents<std::string>(
        index_dir, "Document 2: Another test text.", "Document");
    EXPECT_TRUE(result.find("Document 2: Another test text.") != std::string::npos);
}

TEST_F(TestManageSearchDocuments, test_multiple_updates_on_same_document) {
    manager.manage_and_search_documents<std::string>(
        index_dir, "Updated Document: Final version.");
    std::string result = manager.manage_and_search_documents<std::string>(
        index_dir, "", "Updated");
    EXPECT_TRUE(result.find("Updated Document: Final version.") != std::string::npos);
}

TEST_F(TestManageSearchDocuments, test_search_specific_document) {
    std::string result = manager.manage_and_search_documents<std::string>(
        index_dir, "", "Test");
    EXPECT_TRUE(result.find("Document 1: Test text.") != std::string::npos);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}