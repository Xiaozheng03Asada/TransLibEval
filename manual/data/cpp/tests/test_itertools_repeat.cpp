#include <gtest/gtest.h>
#include "../src/function_itertools_repeat.cpp"

class TestRepeatExample : public testing::Test {
protected:
    RepeatExample calc;
};

TEST_F(TestRepeatExample, RepeatInteger) {
    std::string result = calc.repeat_element(5, 3);
    EXPECT_EQ(result, "5, 5, 5");
}

TEST_F(TestRepeatExample, RepeatString) {
    std::string result = calc.repeat_element("apple", 2);
    EXPECT_EQ(result, "apple, apple");
}

TEST_F(TestRepeatExample, RepeatZeroTimes) {
    std::string result = calc.repeat_element(10, 0);
    EXPECT_EQ(result, "");
}

TEST_F(TestRepeatExample, NonNumeric) {
    std::string result = calc.repeat_element("banana", 4);
    EXPECT_EQ(result, "banana, banana, banana, banana");
}

TEST_F(TestRepeatExample, InvalidInput) {
    EXPECT_THROW({
        calc.repeat_element("apple", boost::any("two"));
    }, std::invalid_argument);
}
int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}