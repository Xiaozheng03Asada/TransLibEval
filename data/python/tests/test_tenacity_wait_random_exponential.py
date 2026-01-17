import unittest
from function_tenacity_wait_random_exponential import RetryFunction
from tenacity import RetryError
import time
from unittest.mock import patch

class TestMightFailFunction(unittest.TestCase):

    def setUp(self):
        self.func = RetryFunction()

    def test_success_after_retry(self):
        result = self.func.might_fail_function(5)
        self.assertEqual(result, 5)

    def test_zero_input(self):
        with self.assertRaises(RetryError) as context:
            self.func.might_fail_function(0)
        self.assertIsInstance(context.exception.last_attempt.exception(), ValueError)

    def test_retry_with_random_exponential_backoff(self):
        start_time = time.time()
        with self.assertRaises(RetryError):
            self.func.might_fail_function(2, extra_sleep=2)
        end_time = time.time()
        total_time = end_time - start_time
        print(f"Test execution time: {total_time:.2f} seconds")
        self.assertGreater(total_time, 6) 
        self.assertLess(total_time, 30)

    def test_success_on_first_try(self):
        result = self.func.might_fail_function(6)
        self.assertEqual(result, 6)

    def test_failure_after_max_attempts(self):
        with self.assertRaises(RetryError) as context:
            self.func.might_fail_function(2)
        self.assertIsInstance(context.exception.last_attempt.exception(), ValueError)

if __name__ == '__main__': 
    unittest.main()
