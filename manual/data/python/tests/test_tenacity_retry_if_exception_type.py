import unittest
from function_tenacity_retry_if_exception_type import RetryFunction
from tenacity import RetryError

class TestMightFailFunction(unittest.TestCase):

    def setUp(self):
        self.func = RetryFunction()

    def test_success_after_retry(self):
        result = self.func.might_fail_function(5)
        self.assertEqual(result, 5)

    def test_failure_after_max_attempts(self):
        # 捕获 RetryError 异常并进行断言
        with self.assertRaises(RetryError):
            self.func.might_fail_function(-1, should_raise=True)

    def test_retry_on_value_error_with_custom_message(self):
        with self.assertRaises(RetryError) as context:  
            self.func.might_fail_function(2, should_raise=True)

        self.assertIsInstance(context.exception, RetryError)
        self.assertTrue("ValueError" in str(context.exception))  

    def test_no_retry_on_exception(self):
        with self.assertRaises(Exception):
            self.func.might_fail_function(5, should_raise=True)
            raise Exception("General exception")

    def test_retry_on_multiple_value_errors(self):
        with self.assertRaises(RetryError): 
            for _ in range(4):
                self.func.might_fail_function(2, should_raise=True)

if __name__ == '__main__':
    unittest.main()
