import unittest
from function_sklearn_SimpleImputer import SimpleImputerFunction

class TestSimpleImputerFunction(unittest.TestCase):

    def test_output_type(self):
        data = "1,2;None,3;4,None;5,6;None,8"
        result = SimpleImputerFunction.quick_sort_from_string(data)
        self.assertTrue(isinstance(result, str))

    def test_different_missing_pattern(self):
        data = "1,2;None,3;4,None;5,6;None,8"
        result = SimpleImputerFunction.quick_sort_from_string(data)
        self.assertTrue("1.0" in result)

    def test_empty_data(self):
        data = ""
        result = SimpleImputerFunction.quick_sort_from_string(data)
        self.assertEqual(result, "")

    def test_no_nan_values(self):
        data = "1,2;2,3;4,5;6,7;8,9"
        result = SimpleImputerFunction.quick_sort_from_string(data)
        self.assertNotIn("None", result)

    def test_different_dimension_data(self):
        data = "1,2;None,4"
        result = SimpleImputerFunction.quick_sort_from_string(data)
        self.assertTrue(isinstance(result, str))
        self.assertTrue("4.0" in result)

if __name__ == '__main__':
    unittest.main()
