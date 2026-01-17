#include <gtest/gtest.h>
#include "../src/function_networkx_connected_components.cpp"

class TestGraphUtils : public ::testing::Test {
protected:
    GraphUtils utils;
};

TEST_F(TestGraphUtils, TestSuccess) {
    std::string result = utils.find_connected_components(5, 4, "A-B, B-C, D-E, E-F");
    EXPECT_TRUE(result.find("success") == 0);
    EXPECT_TRUE(result.find("2 components") != std::string::npos);
}

TEST_F(TestGraphUtils, TestSingleComponent) {
    std::string result = utils.find_connected_components(3, 3, "A-B, B-C, A-C");
    EXPECT_TRUE(result.find("success") == 0);
    EXPECT_TRUE(result.find("1 components") != std::string::npos);
}

TEST_F(TestGraphUtils, TestNoEdges) {
    std::string result = utils.find_connected_components(5, 0, "");
    EXPECT_TRUE(result.find("success") == 0);
    EXPECT_TRUE(result.find("5 components") != std::string::npos);
}

TEST_F(TestGraphUtils, TestSingleNode) {
    std::string result = utils.find_connected_components(1, 0, "");
    EXPECT_TRUE(result.find("success") == 0);
    EXPECT_TRUE(result.find("1 components") != std::string::npos);
}

TEST_F(TestGraphUtils, TestPartialConnection) {
    std::string result = utils.find_connected_components(5, 3, "A-B, B-C, D-E");
    EXPECT_TRUE(result.find("success") == 0);
    EXPECT_TRUE(result.find("2 components") != std::string::npos);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}