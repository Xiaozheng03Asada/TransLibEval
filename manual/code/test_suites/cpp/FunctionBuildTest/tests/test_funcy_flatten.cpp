#include <gtest/gtest.h>
#include "../src/function_funcy_flatten.cpp"

class TestFuncyFlatten : public ::testing::Test {
protected:
    ListProcessor processor;
};

TEST_F(TestFuncyFlatten, FlattenSimpleList) {
    std::string result = processor.process_list("[1, [2, 3], [4, 5], 6]");
    EXPECT_EQ(result, "[1,2,3,4,5,6]");
}

TEST_F(TestFuncyFlatten, FlattenEmptyElements) {
    std::string result = processor.process_list("[[], [], [1, 2], [], [3, 4]]");
    EXPECT_EQ(result, "[1,2,3,4]");
}

TEST_F(TestFuncyFlatten, ComplexNestedStructure) {
    std::string result = processor.process_list("[1, [2, 3, [4, 5]], [6, [7, 8]], 9]");
    EXPECT_EQ(result, "[1,2,3,4,5,6,7,8,9]");
}

TEST_F(TestFuncyFlatten, FlattenWithMixedDataTypes) {
    std::string result = processor.process_list("[1, \"apple\", [3.14, \"banana\"], [2, \"orange\"], [[true, false]]]");
    EXPECT_EQ(result, "[1,\"apple\",3.14,\"banana\",2,\"orange\",true,false]");
}

TEST_F(TestFuncyFlatten, FlattenNonIterable) {
    std::string result = processor.process_list("12345");
    EXPECT_EQ(result, "Error: input is not iterable");
}


int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}