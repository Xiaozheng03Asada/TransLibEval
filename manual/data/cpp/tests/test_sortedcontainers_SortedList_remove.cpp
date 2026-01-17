#include <gtest/gtest.h>
#include <string>
#include "../src/function_sortedcontainers_SortedList_remove.cpp"

class TestSortedListRemoveMethod : public ::testing::Test {
protected:
    HanSeok handler;
};

TEST_F(TestSortedListRemoveMethod, test_remove_element) {
    std::string result = handler.remove_element_from_list(3);
    EXPECT_EQ(result, "1,5,8");
}

TEST_F(TestSortedListRemoveMethod, test_remove_element_not_found) {
    std::string result = handler.remove_element_from_list(10);
    EXPECT_EQ(result, "Value not found");
}

TEST_F(TestSortedListRemoveMethod, test_remove_duplicate_element) {
    std::string result = handler.remove_element_from_list(3, "1,3,3,5");
    EXPECT_EQ(result, "1,3,5");
}

TEST_F(TestSortedListRemoveMethod, test_remove_all_elements) {
    std::string sorted_list = "1,3,5,8";
    std::vector<int> values;
    std::istringstream ss(sorted_list);
    std::string token;
    
    while (std::getline(ss, token, ',')) {
        if (!token.empty()) {
            values.push_back(std::stoi(token));
        }
    }
    
    for (const auto& value : values) {
        sorted_list = handler.remove_element_from_list(value, sorted_list);
    }
    
    EXPECT_EQ(sorted_list, "");
}

TEST_F(TestSortedListRemoveMethod, test_empty_list) {
    std::string result = handler.remove_element_from_list(1, "1");
    EXPECT_EQ(result, "");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}