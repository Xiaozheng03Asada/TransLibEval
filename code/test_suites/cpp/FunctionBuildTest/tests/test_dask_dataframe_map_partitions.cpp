#include <gtest/gtest.h>
#include "../src/function_dask_dataframe_map_partitions.cpp"

class TestComputePartitionMeansFunction : public ::testing::Test {
protected:
    ComputePartitionMeansFunction compute_means_func;
};

TEST_F(TestComputePartitionMeansFunction, TestValidColumn) {
    std::string data = "A,B\n0,1.5\n1,2.5\n2,3.5\n3,4.5\n4,5.5\n5,6.5\n6,7.5\n7,8.5\n8,9.5\n9,10.5";
    std::string result = compute_means_func.compute_partition_means(data, "B");
    EXPECT_EQ(result, "3.5,8.5");
}

TEST_F(TestComputePartitionMeansFunction, TestInvalidColumn) {
    std::string data = "A,B\n0,1.5\n1,2.5\n2,3.5";
    std::string result = compute_means_func.compute_partition_means(data, "C");
    EXPECT_EQ(result, "Error");
}

TEST_F(TestComputePartitionMeansFunction, TestNonNumericColumn) {
    std::string data = "A,B\nx,a\ny,b\nz,c";
    std::string result = compute_means_func.compute_partition_means(data, "B");
    EXPECT_EQ(result, "Error");
}

TEST_F(TestComputePartitionMeansFunction, TestSingleValueColumn) {
    std::string data = "B\n10";
    std::string result = compute_means_func.compute_partition_means(data, "B");
    EXPECT_EQ(result, "10.0");
}

TEST_F(TestComputePartitionMeansFunction, TestAllSameValues) {
    std::string data = "B\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5";
    std::string result = compute_means_func.compute_partition_means(data, "B");
    EXPECT_EQ(result, "5.0,5.0");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}