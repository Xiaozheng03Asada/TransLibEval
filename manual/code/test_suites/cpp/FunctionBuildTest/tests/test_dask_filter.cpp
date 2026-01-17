#include <gtest/gtest.h>
#include "../src/function_dask_filter.cpp"

class TestFilterEvenNumbersDask : public ::testing::Test {
protected:
    FilterEvenNumbersDask filter_func;
};

TEST_F(TestFilterEvenNumbersDask, TestMixedNumbers) {
    std::string data = "1,2,3,4,5";
    std::string result = filter_func.check_discount_for_large_order(data);
    EXPECT_EQ(result, "2,4");
}

TEST_F(TestFilterEvenNumbersDask, TestNegativeNumbers) {
    std::string data = "-10,-9,-8,-7,-6,-5,-4,-3,-2,-1";
    std::string result = filter_func.check_discount_for_large_order(data);
    EXPECT_EQ(result, "-10,-8,-6,-4,-2");
}

TEST_F(TestFilterEvenNumbersDask, TestInvalidFormat) {
    std::string data = "2,4,a,6";
    std::string result = filter_func.check_discount_for_large_order(data);
    EXPECT_EQ(result, "Error");
}

TEST_F(TestFilterEvenNumbersDask, TestLargeDataset) {
    std::string data;
    for (int i = 1; i <= 10000; ++i) {
        data += std::to_string(i);
        if (i < 10000) {
            data += ",";
        }
    }
    std::string expected;
    for (int i = 2; i <= 10000; i += 2) {
        expected += std::to_string(i);
        if (i < 10000) {
            expected += ",";
        }
    }
    std::string result = filter_func.check_discount_for_large_order(data);
    EXPECT_EQ(result, expected);
}

TEST_F(TestFilterEvenNumbersDask, TestEmptyInput) {
    std::string data = "";
    std::string result = filter_func.check_discount_for_large_order(data);
    EXPECT_EQ(result, "");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}