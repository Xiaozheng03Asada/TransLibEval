#include <gtest/gtest.h>
#include "../src/function_functools_singledispatch.cpp"

class TestProcessData : public testing::Test {
protected:
    DataProcessor processor;
};

TEST_F(TestProcessData, StringType) {
    auto result = boost::any_cast<std::string>(
        processor.test_singledispatch(std::string("hello")));
    EXPECT_EQ(result, "HELLO");
}

TEST_F(TestProcessData, IntegerType) {
    auto result = boost::any_cast<int>(
        processor.test_singledispatch(3));
    EXPECT_EQ(result, 6);
}

TEST_F(TestProcessData, FloatType) {
    auto result = boost::any_cast<double>(
        processor.test_singledispatch(2.5));
    EXPECT_EQ(result, 5.0);
}

TEST_F(TestProcessData, NullType) {
    auto result = boost::any_cast<std::string>(
        processor.test_singledispatch(boost::any()));
    EXPECT_EQ(result, "NONE");
}

TEST_F(TestProcessData, UnsupportedType) {
    std::vector<int> vec{1, 2, 3};
    EXPECT_THROW(processor.test_singledispatch(vec), std::runtime_error);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}