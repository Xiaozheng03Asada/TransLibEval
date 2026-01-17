import unittest
from function_tenacity_retry_if_exception_type import RetryFunction
from tenacity import RetryError

class TestMightFailFunction(unittest.TestCase):

    def setUp(self):
        self.func = RetryFunction()

    def test_success_on_first_try(self):
        result = self.func.might_fail_function(6)
        self.assertEqual(result, 6)

    def test_failure_after_max_attempts(self):
        with self.assertRaises(RetryError) as context:
            self.func.might_fail_function(2, should_raise=True)
        self.assertTrue("ValueError" in str(context.exception))

    def test_retry_on_result(self):
        with self.assertRaises(RetryError) as context:
            self.func.might_fail_function(2, should_raise=True)
        self.assertTrue("ValueError" in str(context.exception))

    def test_no_retry_on_result(self):
        result = self.func.might_fail_function(5)
        self.assertEqual(result, 5)

    def test_retry_with_custom_logic(self):
        with self.assertRaises(RetryError) as context:
            self.func.might_fail_function(3, should_raise=True)
        self.assertTrue("ValueError" in str(context.exception))

if __name__ == '__main__':
    unittest.main()
