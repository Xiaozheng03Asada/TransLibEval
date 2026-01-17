#include <gtest/gtest.h>
#include "../src/function_cvxpy_max.cpp"

class TestCVXPYMaxFunction : public ::testing::Test {
protected:
    CVXPYMaxFunction func;
};

TEST_F(TestCVXPYMaxFunction, MaxTypeTest) {
    std::string result = func.compute_max_value("[1, 2, 3, 4]");
    EXPECT_NO_THROW(std::stod(result));
}

TEST_F(TestCVXPYMaxFunction, MaxMixedValuesTest) {
    std::string result = func.compute_max_value("[-10, 0, 5, -3, 9, -1]");
    EXPECT_EQ(result, "9.0");
}

TEST_F(TestCVXPYMaxFunction, MaxSingleValueTest) {
    std::string result = func.compute_max_value("[42]");
    EXPECT_EQ(result, "42.0");
}

TEST_F(TestCVXPYMaxFunction, MaxWithFloatsTest) {
    std::string result = func.compute_max_value("[1.5, 2.75, 0.5, 2.74]");
    EXPECT_EQ(result, "2.75");
}

TEST_F(TestCVXPYMaxFunction, InputWithNanTest) {
    EXPECT_THROW(func.compute_max_value("[1, nan, 2, 3]"), std::invalid_argument);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}