#include <gtest/gtest.h>

#include "../src/function_Whoosh_terms.cpp"

class TestWhooshListTerms : public ::testing::Test {
protected:
    std::string index_dir = "test_list_terms_index";
    WhooshTerms func;

    void SetUp() override {
        cleanup();
        func.manage_index_and_list_terms<std::string>(index_dir);
    }

    void TearDown() override {
        cleanup();
    }

    void cleanup() {
        if (std::filesystem::exists(index_dir)) {
            std::filesystem::remove_all(index_dir);
        }
    }
};

TEST_F(TestWhooshListTerms, test_list_terms) {
    std::string terms = func.manage_index_and_list_terms<std::string>(index_dir);
    EXPECT_TRUE(terms.find("hello") != std::string::npos);
    EXPECT_TRUE(terms.find("test") != std::string::npos);
    EXPECT_GT(terms.size(), 0);
}

TEST_F(TestWhooshListTerms, test_invalid_index_directory) {
    std::string invalid_index_dir = "invalid_index";
    if (std::filesystem::exists(invalid_index_dir)) {
        std::filesystem::remove_all(invalid_index_dir);
    }
    EXPECT_THROW(func.manage_index_and_list_terms<std::string>(invalid_index_dir, "content", false), std::runtime_error);
}

TEST_F(TestWhooshListTerms, test_case_insensitivity) {
    std::string terms = func.manage_index_and_list_terms<std::string>(index_dir);
    EXPECT_TRUE(terms.find("whoosh") != std::string::npos);
    EXPECT_TRUE(terms.find("Whoosh") == std::string::npos);
}

TEST_F(TestWhooshListTerms, test_word_frequency) {
    std::string terms = func.manage_index_and_list_terms<std::string>(index_dir);
    EXPECT_GT(terms.size(), 0);
}

TEST_F(TestWhooshListTerms, test_term_existence) {
    std::string terms = func.manage_index_and_list_terms<std::string>(index_dir);
    EXPECT_TRUE(terms.find("whoosh") != std::string::npos);
    EXPECT_TRUE(terms.find("nonexistentword") == std::string::npos);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}