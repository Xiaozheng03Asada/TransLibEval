#include <gtest/gtest.h>
#include "../src/function_joblib_memory.cpp"

class TestMemoryExample : public testing::Test {
protected:
    MemoryExample calc;
};

TEST_F(TestMemoryExample, ComputeSquare) {
    int result = calc.compute_square(4);
    EXPECT_EQ(result, 16);
}

TEST_F(TestMemoryExample, ComputeSquareAnother) {
    int result = calc.compute_square(5);
    EXPECT_EQ(result, 25);
}

TEST_F(TestMemoryExample, ComputeSquareCache) {
    int result1 = calc.compute_square(4);
    int result2 = calc.compute_square(4);
    EXPECT_EQ(result1, result2);
}

TEST_F(TestMemoryExample, ComputeSquareZero) {
    int result = calc.compute_square(0);
    EXPECT_EQ(result, 0);
}

TEST_F(TestMemoryExample, NonIntegerInput) {
    EXPECT_THROW({
        calc.compute_square("string");
    }, std::invalid_argument);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}