import unittest
import numpy as np
from function_pyfftw_ifftn import IFFTNProcessor

class TestIFFTNFunctions(unittest.TestCase):
    
    def setUp(self):
        self.processor = IFFTNProcessor()

    def test_ifftn_high_dimensional_input(self):
        input_array = "np.random.random((3, 3, 3, 3))"
        result = self.processor.compute_ifftn(input_array)
        self.assertEqual(result, "(3, 3, 3, 3)")

    def test_ifftn_high_dimensional_large_input(self):
        input_array = "np.random.random((2, 2, 2, 2, 2))"
        result = self.processor.compute_ifftn(input_array)
        self.assertEqual(result, "(2, 2, 2, 2, 2)")
        self.assertFalse(np.allclose(eval(input_array), np.zeros((2, 2, 2, 2, 2))))

    def test_ifftn_high_dimensional_large_values(self):
        input_array = "np.ones((4, 4, 4, 4), dtype=float) * 1e10"
        result = self.processor.compute_ifftn(input_array)
        self.assertEqual(result, "(4, 4, 4, 4)")
        self.assertFalse(np.allclose(eval(input_array), np.zeros((4, 4, 4, 4))))

    def test_ifftn_empty_input(self):
        input_array = "np.array([])"
        with self.assertRaises(ValueError):
            self.processor.compute_ifftn(input_array)

    def test_ifftn_large_value(self):
        input_array = "np.ones((4, 4, 4, 4), dtype=float) * 1e10"
        result = self.processor.compute_ifftn(input_array)
        self.assertEqual(result, "(4, 4, 4, 4)")
        self.assertFalse(np.allclose(eval(input_array), np.zeros((4, 4, 4, 4))))

if __name__ == '__main__':
    unittest.main()
