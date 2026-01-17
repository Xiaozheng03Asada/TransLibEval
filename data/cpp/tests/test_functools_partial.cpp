
#include <gtest/gtest.h>
#include "../src/function_functools_partial.cpp"

class TestPartialFunctionExample : public testing::Test {
protected:
    PartialFunctionExample calc;
};

TEST_F(TestPartialFunctionExample, NormalAddition) {
    int result = calc.apply_partial_function(5, 10);
    EXPECT_EQ(result, 15);
}

TEST_F(TestPartialFunctionExample, AdditionWithNegative) {
    int result = calc.apply_partial_function(-3, 7);
    EXPECT_EQ(result, 4);
}

TEST_F(TestPartialFunctionExample, AdditionWithZero) {
    int result = calc.apply_partial_function(0, 5);
    EXPECT_EQ(result, 5);
}TEST_F(TestPartialFunctionExample, NonNumericInput) {
    EXPECT_THROW({
        calc.apply_partial_function("five", 10);
    }, std::invalid_argument);
}

TEST_F(TestPartialFunctionExample, EdgeCase) {
    int result = calc.apply_partial_function(0, 0);
    EXPECT_EQ(result, 0);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}