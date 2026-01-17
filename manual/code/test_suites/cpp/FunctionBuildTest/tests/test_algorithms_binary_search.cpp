#include <gtest/gtest.h>
#include "../src/function_algorithms_binary_search.cpp"

class TestBinarySearchFunction : public ::testing::Test {
protected:
    BinarySearchFunction function;
};

TEST_F(TestBinarySearchFunction, TestTargetPresentInString) {
    std::string data = "246810";
    char target = '6';
    EXPECT_EQ(function.binary_search_index(data, target), 2);
}

TEST_F(TestBinarySearchFunction, TestTargetAbsentInString) {
    std::string data = "246810";
    char target = '5';
    EXPECT_EQ(function.binary_search_index(data, target), -1);
}

TEST_F(TestBinarySearchFunction, TestEmptyString) {
    std::string data = "";
    char target = '1';
    EXPECT_EQ(function.binary_search_index(data, target), -1);
}

TEST_F(TestBinarySearchFunction, TestSingleCharacterFound) {
    std::string data = "7";
    char target = '7';
    EXPECT_EQ(function.binary_search_index(data, target), 0);
}

TEST_F(TestBinarySearchFunction, TestSingleCharacterNotFound) {
    std::string data = "7";
    char target = '3';
    EXPECT_EQ(function.binary_search_index(data, target), -1);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}