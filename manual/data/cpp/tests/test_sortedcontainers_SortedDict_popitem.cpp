#include <gtest/gtest.h>
#include "../src/function_sortedcontainers_SortedDict_popitem.cpp"

class TestSortedDictPopitemMethod : public ::testing::Test {
protected:
    SortedDictHandler handler;
};

TEST_F(TestSortedDictPopitemMethod, test_pop_last_item) {
    std::string result = handler.modify_sorted_dict(-1);
    EXPECT_EQ(result, "{1: 'one', 3: 'three'} (5, 'five')");
}

TEST_F(TestSortedDictPopitemMethod, test_pop_first_item) {
    std::string result = handler.modify_sorted_dict(0);
    EXPECT_EQ(result, "{3: 'three', 5: 'five'} (1, 'one')");
}

TEST_F(TestSortedDictPopitemMethod, test_pop_invalid_index) {
    std::string result = handler.modify_sorted_dict(5);
    EXPECT_EQ(result, "error: Invalid index");
}

TEST_F(TestSortedDictPopitemMethod, test_pop_middle_item) {
    std::string result = handler.modify_sorted_dict(1);
    EXPECT_EQ(result, "{1: 'one', 5: 'five'} (3, 'three')");
}

TEST_F(TestSortedDictPopitemMethod, test_pop_all_items) {
    std::string result = handler.modify_sorted_dict(0);
    EXPECT_EQ(result, "{3: 'three', 5: 'five'} (1, 'one')");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}