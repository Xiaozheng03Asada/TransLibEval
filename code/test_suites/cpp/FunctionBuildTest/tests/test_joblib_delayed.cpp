#include <gtest/gtest.h>
#include "../src/function_joblib_delayed.cpp"
class TestDelayedExample : public testing::Test {
protected:
    DelayedExample calc;
};

TEST_F(TestDelayedExample, ApplyDelayedFunction) {
    std::string result = calc.apply_delayed_function(5, 10);
    EXPECT_EQ(result, "15");
}

TEST_F(TestDelayedExample, ApplyDelayedFunctionNegative) {
    std::string result = calc.apply_delayed_function(-3, 7);
    EXPECT_EQ(result, "4");
}

TEST_F(TestDelayedExample, ApplyDelayedFunctionZero) {
    std::string result = calc.apply_delayed_function(0, 5);
    EXPECT_EQ(result, "5");
}

TEST_F(TestDelayedExample, NonNumericInput) {
    EXPECT_THROW({
        calc.apply_delayed_function("five", 10);
    }, std::invalid_argument);
}

TEST_F(TestDelayedExample, EdgeCase) {
    std::string result = calc.apply_delayed_function(0, 0);
    EXPECT_EQ(result, "0");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}