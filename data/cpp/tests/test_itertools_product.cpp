
#include <gtest/gtest.h>
#include "../src/function_itertools_product.cpp"

class TestCartesianProduct : public testing::Test {
protected:
    CartesianProductProcessor processor;
};

TEST_F(TestCartesianProduct, CartesianProductOneElementInThreeLists) {
    std::string result = processor.test_product("1;2,3;4,5");
    std::vector<std::string> expected = {"124", "125", "134", "135"};
    std::vector<std::string> actual;
    boost::split(actual, result, boost::is_any_of(";"));
    std::sort(actual.begin(), actual.end());
    std::sort(expected.begin(), expected.end());
    EXPECT_EQ(actual, expected);
}

TEST_F(TestCartesianProduct, CartesianProductNegativeNumbers) {
    std::string result = processor.test_product("-1,0;-2,2");
    std::vector<std::string> expected = {"-1-2", "-12", "0-2", "02"};
    std::vector<std::string> actual;
    boost::split(actual, result, boost::is_any_of(";"));
    std::sort(actual.begin(), actual.end());
    std::sort(expected.begin(), expected.end());
    EXPECT_EQ(actual, expected);
}

TEST_F(TestCartesianProduct, CartesianProductNullInput) {
    EXPECT_THROW(processor.test_product(boost::any()), std::invalid_argument);
}

TEST_F(TestCartesianProduct, CartesianProductNonIterableInput) {
    EXPECT_THROW(processor.test_product(123), std::invalid_argument);
}

TEST_F(TestCartesianProduct, CartesianProductElementsNotIterable) {
    EXPECT_THROW(processor.test_product("1,2;;4,5"), std::invalid_argument);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}