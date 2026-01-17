#include <gtest/gtest.h>
#include "../src/function_cvxpy_variable.cpp"

class TestCVXPYVariableFunction : public ::testing::Test {
protected:
    CVXPYVariableFunction func;
};

TEST_F(TestCVXPYVariableFunction, VariableValueAssignment) {
    std::string result1 = func.process_variables("5", "10");
    EXPECT_EQ(result1, "x: 5.0, y: 10.0");

    std::string result2 = func.process_variables("0", "0");
    EXPECT_EQ(result2, "x: 0.0, y: 0.0");
}

TEST_F(TestCVXPYVariableFunction, UnassignedVariable) {
    std::string result = func.process_variables();
    EXPECT_EQ(result, "x, y");
}

TEST_F(TestCVXPYVariableFunction, VariableNegativeSize) {
    std::string result = func.process_variables("-1", "10");
    EXPECT_EQ(result, "Error: Variable size cannot be negative");
}

TEST_F(TestCVXPYVariableFunction, InvalidVariableConstraint) {
    std::string result = func.process_variables("", "", "string_value");
    EXPECT_EQ(result, "Error: Invalid variable constraint");
}

TEST_F(TestCVXPYVariableFunction, InvalidInput) {
    std::string result = func.process_variables("abc", "xyz");
    EXPECT_EQ(result, "Error: Invalid value for x or y");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}