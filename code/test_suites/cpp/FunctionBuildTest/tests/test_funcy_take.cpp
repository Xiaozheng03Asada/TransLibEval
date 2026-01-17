#include <gtest/gtest.h>
#include "../src/function_funcy_take.cpp"

class TestFuncyTake : public ::testing::Test {
protected:
    IterableProcessor processor;
};

TEST_F(TestFuncyTake, GetFirstNElements) {
    std::string result = processor.get_first_n_elements("[1, 2, 3, 4, 5]", 3);
    EXPECT_EQ(result, "[1,2,3]");
}

TEST_F(TestFuncyTake, MoreElementsThanList) {
    std::string result = processor.get_first_n_elements("[1, 2]", 5);
    EXPECT_EQ(result, "[1,2]");
}

TEST_F(TestFuncyTake, TakeLargeN) {
    std::string result = processor.get_first_n_elements("[1, 2, 3]", 100);
    EXPECT_EQ(result, "[1,2,3]");
}

TEST_F(TestFuncyTake, TakeWithStrings) {
    std::string result = processor.get_first_n_elements("[\"apple\", \"banana\", \"cherry\", \"date\"]", 2);
    EXPECT_EQ(result, "[\"apple\",\"banana\"]");
}

TEST_F(TestFuncyTake, InvalidInput) {
    std::string result = processor.get_first_n_elements("12345", 3);
    EXPECT_EQ(result, "Error: invalid input");
}


int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}