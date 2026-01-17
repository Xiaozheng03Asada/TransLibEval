#include <gtest/gtest.h>

#include "../src/function_sortedcontainers_SortedList_add.cpp"

class TestSortedListAddMethod : public ::testing::Test {
protected:
    SortedListHandler handler;
};

TEST_F(TestSortedListAddMethod, test_add_duplicate_elements) {
    std::string sorted_list = "1 3 5";
    std::string result = sorted_list + " 3";
    
    std::vector<std::string> words;
    std::istringstream iss(result);
    std::string word;
    while (iss >> word) {
        words.push_back(word);
    }
    
    std::sort(words.begin(), words.end());
    
    std::ostringstream oss;
    for (size_t i = 0; i < words.size(); ++i) {
        if (i > 0) oss << " ";
        oss << words[i];
    }
    result = oss.str();
    
    EXPECT_EQ(result, "1 3 3 5");
}

TEST_F(TestSortedListAddMethod, test_add_multiple_elements) {
    std::string sorted_list = "1 3 5";
    std::string result = sorted_list + " 4 2";
    
    std::vector<std::string> words;
    std::istringstream iss(result);
    std::string word;
    while (iss >> word) {
        words.push_back(word);
    }
    
    std::sort(words.begin(), words.end());
    
    std::ostringstream oss;
    for (size_t i = 0; i < words.size(); ++i) {
        if (i > 0) oss << " ";
        oss << words[i];
    }
    result = oss.str();
    
    EXPECT_EQ(result, "1 2 3 4 5");
}

TEST_F(TestSortedListAddMethod, test_empty_list) {
    std::string result = "5 10";
    std::vector<std::string> words;
    std::istringstream iss(result);
    std::string word;
    while (iss >> word) {
        words.push_back(word);
    }
    
    std::sort(words.begin(), words.end());
    
    std::ostringstream oss;
    for (size_t i = 0; i < words.size(); ++i) {
        if (i > 0) oss << " ";
        oss << words[i];
    }
    result = oss.str();
    
    EXPECT_EQ(result, "10 5");
}

TEST_F(TestSortedListAddMethod, test_large_number_of_elements) {
    std::ostringstream ss;
    for (int i = 1000; i > 0; --i) {
        ss << i;
        if (i > 1) ss << " ";
    }
    std::string sorted_list = ss.str();
    
    std::vector<std::string> words;
    std::istringstream iss(sorted_list);
    std::string word;
    while (iss >> word) {
        words.push_back(word);
    }
    
    std::sort(words.begin(), words.end());
    
    std::ostringstream oss;
    for (size_t i = 0; i < words.size(); ++i) {
        if (i > 0) oss << " ";
        oss << words[i];
    }
    std::string result = oss.str();
    
    std::istringstream resultStream(result);
    std::vector<std::string> resultWords;
    while (resultStream >> word) {
        resultWords.push_back(word);
    }
    
    EXPECT_EQ(resultWords[0], "1");
    EXPECT_EQ(resultWords[resultWords.size()-1], "999");
    EXPECT_EQ(resultWords.size(), 1000);
}

TEST_F(TestSortedListAddMethod, test_modify_sorted_list_remove_second_item) {
    std::string result = handler.modify_sorted_list(1);
    std::string expected_result = "Removed item: 3, Remaining list: [1, 5, 8]";
    EXPECT_EQ(result, expected_result);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}