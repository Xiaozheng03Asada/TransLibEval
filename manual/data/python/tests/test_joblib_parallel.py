import unittest
import math
from function_joblib_parallel import ParallelProcessor

class TestParallelFunction(unittest.TestCase):

    def test_parallel_function_basic(self):
        processor = ParallelProcessor()
        result_1 = processor.run_parallel(0, 1, 2, 3, 4, n_jobs=2)
        self.assertEqual(len(result_1.split(', ')), 5)

    def test_parallel_function_empty_data(self):
        processor = ParallelProcessor()
        result_6 = processor.run_parallel(n_jobs=2)
        self.assertEqual(result_6, '')

    def test_parallel_function_mixed_data_types(self):
        processor = ParallelProcessor()
        result_10 = processor.run_parallel(1, 2.5, 3 + 4j, n_jobs=2)
        self.assertEqual(len(result_10.split(', ')), 3)

    def test_parallel_function_data_with_max_int(self):
        processor = ParallelProcessor()
        result_13 = processor.run_parallel(1, math.pow(2, 31) - 1, n_jobs=2)
        self.assertEqual(len(result_13.split(', ')), 2)

    def test_parallel_function_random_data(self):
        import random
        processor = ParallelProcessor()
        data_14_0 = random.randint(-1000, 1000)
        data_14_1 = random.randint(-1000, 1000)
        result_14 = processor.run_parallel(data_14_0, data_14_1, n_jobs=-1)
        self.assertEqual(len(result_14.split(', ')), 2)

if __name__ == '__main__':
    unittest.main()
