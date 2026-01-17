#include <gtest/gtest.h>
#include "../src/function_dask_bag_fold.cpp"

class TestCumulativeSumFunction : public ::testing::Test {
protected:
    CumulativeSumFunction cumsum_func;
};

TEST_F(TestCumulativeSumFunction, TestBasicSum) {
    std::string result = cumsum_func.cumulative_sum("1,2,3,4,5");
    EXPECT_EQ(result, "15.0");
}

TEST_F(TestCumulativeSumFunction, TestNegativeNumbers) {
    std::string result = cumsum_func.cumulative_sum("10,-2,-3,5");
    EXPECT_EQ(result, "10.0");
}

TEST_F(TestCumulativeSumFunction, TestAlternatingSigns) {
    std::string result = cumsum_func.cumulative_sum("1,-1,2,-2,3,-3");
    EXPECT_EQ(result, "0.0");
}

TEST_F(TestCumulativeSumFunction, TestLargeNumbers) {
    std::string result = cumsum_func.cumulative_sum("1000000,1000000,1000000");
    EXPECT_EQ(result, "3000000.0");
}

TEST_F(TestCumulativeSumFunction, TestInvalidInput) {
    std::string result = cumsum_func.cumulative_sum("1,a,None,4");
    EXPECT_EQ(result, "Error");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}