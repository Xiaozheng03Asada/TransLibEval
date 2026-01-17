#include <gtest/gtest.h>
#include "../src/function_networkx_dijkstra_path.cpp"

class TestNetworkXExample : public ::testing::Test {
protected:
    NetworkXExample calc;
};

TEST_F(TestNetworkXExample, TestShortestPath) {
    std::string result = calc.compute_shortest_path("A", "C", "A", "B", 1.0f, "B", "C", 1.0f);
    EXPECT_EQ(result, "A -> B -> C");
}

TEST_F(TestNetworkXExample, TestNoPath) {
    std::string result = calc.compute_shortest_path("A", "D", "A", "B", 1.0f, "B", "C", 2.0f);
    EXPECT_EQ(result, "no path");
}

TEST_F(TestNetworkXExample, TestSingleEdge) {
    std::string result = calc.compute_shortest_path("A", "B", "A", "B", 5.0f, "B", "B", 0.0f);
    EXPECT_EQ(result, "A -> B");
}

TEST_F(TestNetworkXExample, TestMultiplePaths) {
    std::string result = calc.compute_shortest_path("A", "C", "A", "B", 1.0f, "B", "C", 2.0f);
    EXPECT_EQ(result, "A -> B -> C");
}

TEST_F(TestNetworkXExample, TestMultipleEdgesWithDifferentWeights) {
    std::string result = calc.compute_shortest_path("A", "C", "A", "B", 5.0f, "B", "C", 1.0f);
    EXPECT_EQ(result, "A -> B -> C");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}