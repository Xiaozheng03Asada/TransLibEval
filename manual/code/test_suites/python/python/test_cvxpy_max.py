import unittest
from function_cvxpy_max import CVXPYMaxFunction

class TestCVXPYMaxFunction(unittest.TestCase):
    
    def test_max_type(self):
        vector = "[1, 2, 3, 4]"
        max_value = CVXPYMaxFunction().compute_max_value(vector)
        self.assertIsInstance(max_value, str) 

    def test_max_mixed_values(self):
        vector = "[-10, 0, 5, -3, 9, -1]"
        max_value = CVXPYMaxFunction().compute_max_value(vector)
        self.assertEqual(max_value, "9.0") 

    def test_max_single_value(self):
        vector = "[42]"
        max_value = CVXPYMaxFunction().compute_max_value(vector)
        self.assertEqual(max_value, "42.0")  

    def test_max_with_floats(self):
        vector = "[1.5, 2.75, 0.5, 2.74]"
        max_value = CVXPYMaxFunction().compute_max_value(vector)
        self.assertEqual(max_value, "2.75")

    def test_input_with_nan(self):
        with self.assertRaises(ValueError):
            vector = "[1, nan, 2, 3]"
            max_value = CVXPYMaxFunction().compute_max_value(vector)
            max_value  

if __name__ == "__main__":
    unittest.main()
