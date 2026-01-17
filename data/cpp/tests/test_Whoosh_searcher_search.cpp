#include <gtest/gtest.h>
#include <string>
#include <filesystem>
#include <fstream>
#include "../src/function_Whoosh_searcher_search.cpp"

namespace fs = std::filesystem;

class TestWhooshSearch : public ::testing::Test {
protected:
    std::string index_dir = "test_search_index";
    WhooshSearcher searcher;

    void SetUp() override {
        create_index(index_dir);
    }

    void TearDown() override {
        if (fs::exists(index_dir)) {
            fs::remove_all(index_dir);
        }
    }

    void create_index(const std::string& index_dir) {
        if (!fs::exists(index_dir)) {
            fs::create_directory(index_dir);
        }

        Xapian::WritableDatabase db(index_dir, Xapian::DB_CREATE_OR_OPEN);

        // 添加文档
        Xapian::Document doc1;
        doc1.set_data("This is a test document.");
        doc1.add_term("test");
        doc1.add_term("document");
        db.add_document(doc1);

        Xapian::Document doc2;
        doc2.set_data("Another test document.");
        doc2.add_term("test");
        doc2.add_term("document");
        db.add_document(doc2);

        Xapian::Document doc3;
        doc3.set_data("Document with a different content.");
        doc3.add_term("document");
        doc3.add_term("content");
        db.add_document(doc3);
    }
};

TEST_F(TestWhooshSearch, test_search_document) {
    std::string result = searcher.perform_search(index_dir, "test");
    EXPECT_EQ(result, "This is a test document.,Another test document.");
}

TEST_F(TestWhooshSearch, test_search_no_results) {
    std::string result = searcher.perform_search(index_dir, "nonexistent");
    EXPECT_EQ(result, "No documents found");
}

TEST_F(TestWhooshSearch, test_search_multiple_matches) {
    std::string result = searcher.perform_search(index_dir, "document");
    EXPECT_EQ(result, "This is a test document., Document with a different content.,Another test document.");
}

TEST_F(TestWhooshSearch, test_empty_query) {
    std::string result = searcher.perform_search(index_dir, "");
    EXPECT_EQ(result, "No documents found");
}

TEST_F(TestWhooshSearch, test_invalid_index_directory) {
    EXPECT_THROW(searcher.perform_search("invalid_dir", "test"), std::invalid_argument);
}