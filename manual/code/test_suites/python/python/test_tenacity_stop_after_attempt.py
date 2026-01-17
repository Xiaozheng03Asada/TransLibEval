import unittest
from function_tenacity_stop_after_attempt import RetryFunction

class TestMightFailFunction(unittest.TestCase):
    def setUp(self):
        self.func = RetryFunction()

    def test_success_on_first_try(self):
        result = self.func.might_fail_function(6)
        self.assertEqual(result, 6)

    def test_success_after_retry(self):
        result = self.func.might_fail_function(5)
        self.assertEqual(result, 5)

    def test_no_retry_on_too_small_value(self):
        with self.assertRaises(ValueError):
            self.func.might_fail_function(1, handle_retry_error=False)

    def test_runtimeerror_on_too_small_value(self):
        with self.assertRaises(RuntimeError) as context:
            self.func.might_fail_function(1, handle_retry_error=True)
        self.assertIn("Operation failed after retries", str(context.exception))  

    def test_runtimeerror_with_extra_sleep(self):
        with self.assertRaises(RuntimeError) as context:
            self.func.might_fail_function(4, extra_sleep=0.5, handle_retry_error=True)
        self.assertIn("Operation failed after retries", str(context.exception))  

if __name__ == '__main__':
    unittest.main()
