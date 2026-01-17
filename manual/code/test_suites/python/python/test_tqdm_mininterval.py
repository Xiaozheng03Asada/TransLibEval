import unittest
import time
from unittest.mock import patch
from function_tqdm_mininterval import ProgressBar


class TestMinintervalFunction(unittest.TestCase):

    @patch('time.sleep')
    def test_mininterval_very_small(self, mock_sleep):
        start_time = time.time()
        result = ProgressBar.might_fail_function(0.01)
        elapsed_time = time.time() - start_time
        
        self.assertTrue(elapsed_time < 0.1 * 10)
        self.assertEqual(result, 10)

    @patch('time.sleep')
    def test_mininterval_very_large(self, mock_sleep):
        start_time = time.time()
        result = ProgressBar.might_fail_function(2)
        elapsed_time = time.time() - start_time
        
        self.assertTrue(elapsed_time < 2 * 10)
        self.assertEqual(result, 10)

    @patch('time.sleep')
    def test_mininterval_zero(self, mock_sleep):
        start_time = time.time()
        result = ProgressBar.might_fail_function(0)
        elapsed_time = time.time() - start_time
        
        self.assertTrue(elapsed_time > 0 and elapsed_time < 2)
        self.assertEqual(result, 10)

    def test_mininterval_non_numeric(self):
        try:
            ProgressBar.might_fail_function('abc')
        except Exception as e:
            self.assertEqual(type(e).__name__, 'TypeError')
        else:
            self.fail("Expected TypeError for non-numeric mininterval")

    @patch('time.sleep')
    def test_mininterval_modify_during_loop(self, mock_sleep):
        result_1 = ProgressBar.might_fail_function(0.3)
        self.assertEqual(result_1, 10)

        result_2 = ProgressBar.might_fail_function(0.1)
        self.assertEqual(result_2, 10)


if __name__ == '__main__':
    unittest.main()
