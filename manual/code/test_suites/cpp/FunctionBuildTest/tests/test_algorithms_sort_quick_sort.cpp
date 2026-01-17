#include <gtest/gtest.h>
#include "../src/function_algorithms_sort_quick_sort.cpp"

class TestQuickSort : public ::testing::Test {
protected:
    QuickSortFunction function;
};

TEST_F(TestQuickSort, TestNormalList) {
    std::string data_str = "4,2,9,1,7";
    std::string expected = "1,2,4,7,9";
    EXPECT_EQ(function.quick_sort_from_string(data_str), expected);
}

TEST_F(TestQuickSort, TestAlreadySorted) {
    std::string data_str = "1,2,3,4,5";
    std::string expected = "1,2,3,4,5";
    EXPECT_EQ(function.quick_sort_from_string(data_str), expected);
}

TEST_F(TestQuickSort, TestReverseSorted) {
    std::string data_str = "5,4,3,2,1";
    std::string expected = "1,2,3,4,5";
    EXPECT_EQ(function.quick_sort_from_string(data_str), expected);
}

TEST_F(TestQuickSort, TestEmptyList) {
    std::string data_str = "";
    std::string expected = "";
    EXPECT_EQ(function.quick_sort_from_string(data_str), expected);
}

TEST_F(TestQuickSort, TestSingleElement) {
    std::string data_str = "42";
    std::string expected = "42";
    EXPECT_EQ(function.quick_sort_from_string(data_str), expected);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}