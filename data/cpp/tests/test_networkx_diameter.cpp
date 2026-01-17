#include <gtest/gtest.h>
#include "../src/function_networkx_diameter.cpp"

class TestCalculateDiameter : public ::testing::Test {
protected:
    GraphUtils utils;
};

TEST_F(TestCalculateDiameter, TestSuccess) {
    std::string result = utils.calculate_diameter("A-B,B-C,C-D,D-E");
    EXPECT_TRUE(result.find("success") == 0);
}

TEST_F(TestCalculateDiameter, TestDiameterValue) {
    std::string result = utils.calculate_diameter("A-B,B-C,C-D,D-E");
    EXPECT_TRUE(result.find("success") == 0);
    if (result.find("success") == 0) {
        int diameter = std::stoi(result.substr(result.find(":") + 1));
        EXPECT_EQ(diameter, 4);
    }
}

TEST_F(TestCalculateDiameter, TestNonNumeric) {
    std::string result = utils.calculate_diameter("A-B,B-C,C-D,D-E");
    EXPECT_TRUE((std::is_same_v<decltype(result), std::string>));
}

TEST_F(TestCalculateDiameter, TestDisconnectedGraph) {
    std::string result = utils.calculate_diameter("A-B,C-D");
    EXPECT_EQ(result, "failed");
}

TEST_F(TestCalculateDiameter, TestEmptyGraph) {
    std::string result = utils.calculate_diameter("");
    EXPECT_EQ(result, "failed");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}