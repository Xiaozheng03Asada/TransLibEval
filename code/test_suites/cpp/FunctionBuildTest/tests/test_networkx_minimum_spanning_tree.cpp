
#include <gtest/gtest.h>
#include "../src/function_networkx_minimum_spanning_tree.cpp"

class TestNetworkXExample : public ::testing::Test {
protected:
    NetworkXExample calc;
};

TEST_F(TestNetworkXExample, TestBasicMST) {
    float result = calc.compute_minimum_spanning_tree("A", "B", 4, "B", "C", 3);
    EXPECT_FLOAT_EQ(result, 7.0);
}

TEST_F(TestNetworkXExample, TestMSTMultipleEdges) {
    float result = calc.compute_minimum_spanning_tree("A", "B", 1, "B", "C", 2);
    EXPECT_FLOAT_EQ(result, 3.0);
}

TEST_F(TestNetworkXExample, TestMSTNoEdges) {
    float result = calc.compute_minimum_spanning_tree("A", "B", 0, "B", "C", 0);
    EXPECT_FLOAT_EQ(result, 0.0);
}

TEST_F(TestNetworkXExample, TestMSTSingleEdge) {
    float result = calc.compute_minimum_spanning_tree("A", "B", 10, "B", "C", 0);
    EXPECT_FLOAT_EQ(result, 10.0);
}

TEST_F(TestNetworkXExample, TestMSTDisconnectedGraph) {
    float result = calc.compute_minimum_spanning_tree("A", "B", 5, "C", "D", 7);
    EXPECT_FLOAT_EQ(result, 12.0);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}