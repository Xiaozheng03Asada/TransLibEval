import unittest
from function_pyfftw_interfaces_numpy_fft_rfft import RFFTProcessor

class TestRFFTFunctions(unittest.TestCase):
    
    def setUp(self):
        self.processor = RFFTProcessor()

    def test_rfft_simple_array(self):
        input_array = "np.array([1.0, 2.0, 3.0, 4.0])"
        result = self.processor.compute_rfft(input_array)
        self.assertEqual(result, "(3,)")

    def test_rfft_zeros(self):
        input_array = "np.zeros(8)"
        result = self.processor.compute_rfft(input_array)
        self.assertEqual(result, "(5,)") 

    def test_rfft_large_input(self):
        input_array = "np.random.random(1024)"
        result = self.processor.compute_rfft(input_array)
        self.assertEqual(result, "(513,)")

    def test_rfft_invalid_input(self):
        input_array = "np.array([\"a\", \"b\", \"c\", \"d\"])"  
        with self.assertRaises(ValueError): 
            self.processor.compute_rfft(input_array)

    def test_rfft_empty_input(self):
        input_array = "np.array([])"
        with self.assertRaises(ValueError): 
            self.processor.compute_rfft(input_array)

if __name__ == '__main__':
    unittest.main()
