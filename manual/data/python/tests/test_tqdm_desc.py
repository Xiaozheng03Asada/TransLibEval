import unittest
from function_tqdm_desc import ProgressBar

class TestDescExample(unittest.TestCase):

    def test_desc_default_value_when_calling_function(self):
        data = "123"
        try:
            result = ProgressBar.process_with_progress_bar(data, None)
            self.assertEqual(type(result), str)
        except Exception as e:
            self.fail(f"tqdm without desc parameter in function raised an unexpected exception: {e}")

    def test_desc_type_check_when_calling_function(self):
        data = "123"
        with self.assertRaises(TypeError):
            ProgressBar.process_with_progress_bar(data, 123)  # 故意传递一个非字符串的类型

    def test_desc_immutability_when_calling_function(self):
        data = "123"
        try:
            _ = ProgressBar.process_with_progress_bar(data, 'Initial')
        except Exception as e:
            self.fail(f"Unexpected exception when testing desc immutability: {e}")

    def test_desc_processed_output_when_calling_function(self):
        data = "123"
        result = ProgressBar.process_with_progress_bar(data, 'Same')
        expected_result = "246"  # 每个字符被乘以2后的结果
        self.assertEqual(result, expected_result)

    def test_desc_length_limit_in_memory_when_calling_function(self):
        long_desc = "a" * 10000  
        data = "1"
        try:
            for _ in range(5): 
                result = ProgressBar.process_with_progress_bar(data, long_desc)
                self.assertEqual(type(result), str)
        except Exception as e:
            self.fail(f"Creating multiple tqdm instances with long desc in function raised an exception: {e}")

if __name__ == '__main__':
    unittest.main()
