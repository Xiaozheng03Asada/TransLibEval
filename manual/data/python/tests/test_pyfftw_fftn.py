import unittest
import numpy as np
from function_pyfftw_fftn import FFTNProcessor

class TestFFTNFunctions(unittest.TestCase):
    
    def setUp(self):
        self.processor = FFTNProcessor()

    def test_compute_fftn(self):
        input_array = "[[1, 2, 3], [4, 5, 6], [7, 8, 9]]"
        result = self.processor.compute_fftn(input_array)
        self.assertEqual(result, "(3, 3)")

    def test_fftn_zero(self):
        input_array = "[[0, 0, 0], [0, 0, 0], [0, 0, 0]]"
        result = self.processor.compute_fftn(input_array)
        self.assertEqual(result, "(3, 3)")

    def test_fftn_shape(self):
        input_array = "[[1, 2], [3, 4]]"
        result = self.processor.compute_fftn(input_array)
        self.assertEqual(result, "(2, 2)")

    def test_fftn_complex_input(self):
        input_array = "[[1+1j, 2+2j], [3+3j, 4+4j]]"
        result = self.processor.compute_fftn(input_array)
        self.assertEqual(result, "(2, 2)")

    def test_fftn_high_dimensional_large_input(self):
        input_array = "[[[[0.1, 0.2], [0.3, 0.4]], [[0.5, 0.6], [0.7, 0.8]]]]"
        result = self.processor.compute_fftn(input_array)
        self.assertEqual(result, "(1, 2, 2, 2)")

if __name__ == '__main__':
    unittest.main()
