import unittest
from function_dask_dataframe_map_partitions import ComputePartitionMeansFunction

class TestComputePartitionMeansFunction(unittest.TestCase):

    def setUp(self):
        self.compute_means_func = ComputePartitionMeansFunction()

    def test_valid_column(self):
        data = "A,B\n0,1.5\n1,2.5\n2,3.5\n3,4.5\n4,5.5\n5,6.5\n6,7.5\n7,8.5\n8,9.5\n9,10.5"
        result = self.compute_means_func.compute_partition_means(data, "B")
        self.assertEqual(result, "3.5,8.5")

    def test_invalid_column(self):
        data = "A,B\n0,1.5\n1,2.5\n2,3.5"
        result = self.compute_means_func.compute_partition_means(data, "C")
        self.assertEqual(result, "Error")

    def test_non_numeric_column(self):
        data = "A,B\nx,a\ny,b\nz,c"
        result = self.compute_means_func.compute_partition_means(data, "B")
        self.assertEqual(result, "Error")

    def test_single_value_column(self):
        data = "B\n10"
        result = self.compute_means_func.compute_partition_means(data, "B")
        self.assertEqual(result, "10.0")

    def test_all_same_values(self):
        data = "B\n5\n5\n5\n5\n5\n5\n5\n5\n5\n5"
        result = self.compute_means_func.compute_partition_means(data, "B")
        self.assertEqual(result, "5.0,5.0")

if __name__ == "__main__":
    unittest.main()
