import unittest
from function_cvxpy_norm import CVXPYNormFunction

class TestCVXPYNormFunction(unittest.TestCase):

    def test_norm_type(self):
        vector = "[1, 2, 3]"  
        norm_value = CVXPYNormFunction().compute_norm(vector)
        self.assertIsInstance(norm_value, str)  

    def test_norm_computation_2_norm(self):
        vector = "[3, 4, 0]"
        norm_value = CVXPYNormFunction().compute_norm(vector, p=2)
        self.assertEqual(norm_value, "5.0") 

    def test_norm_computation_1_norm(self):
        vector = "[1, -2, 3]"  
        norm_value = CVXPYNormFunction().compute_norm(vector, p=1)
        self.assertEqual(norm_value, "6.0") 

    def test_norm_computation_inf_norm(self):
        vector = "[-1, 2, -3, 4]" 
        norm_value = CVXPYNormFunction().compute_norm(vector, p='inf')
        self.assertEqual(norm_value, "4.0")  

    def test_norm_zero_vector(self):
        vector = "[0, 0, 0, 0, 0]"  
        norm_value = CVXPYNormFunction().compute_norm(vector, p=2)
        self.assertEqual(norm_value, "0.0") 

if __name__ == "__main__":
    unittest.main()
