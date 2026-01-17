#include <gtest/gtest.h>
#include "../src/function_algorithms_search_linear_search.cpp"

class TestLinearSearch : public ::testing::Test {
protected:
    LinearSearchFunction function;
};

TEST_F(TestLinearSearch, TestElementFound) {
    EXPECT_EQ(function.linear_search_from_string("10,20,30,40,50", 30), 2);
}

TEST_F(TestLinearSearch, TestElementNotFound) {
    EXPECT_EQ(function.linear_search_from_string("10,20,30,40,50", 60), -1);
}

TEST_F(TestLinearSearch, TestEmptyArray) {
    EXPECT_EQ(function.linear_search_from_string("", 30), -1);
}

TEST_F(TestLinearSearch, TestFirstElement) {
    EXPECT_EQ(function.linear_search_from_string("10,20,30,40,50", 10), 0);
}

TEST_F(TestLinearSearch, TestLastElement) {
    EXPECT_EQ(function.linear_search_from_string("10,20,30,40,50", 50), 4);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}