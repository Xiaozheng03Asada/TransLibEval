import unittest
from function_pyfftw_interfaces_numpy_fft_ifft import IFFTProcessor

class TestIFFTFunction(unittest.TestCase):
    
    def setUp(self):
        self.processor = IFFTProcessor()

    def test_ifft_simple_input(self):
        input_array = "np.array([1 + 2j, 3 + 4j, 5 + 6j, 7 + 8j])"
        result = self.processor.compute_ifft(input_array)
        self.assertEqual(result, "(4,)")  

    def test_ifft_zero_input(self):
        input_array = "np.zeros(4, dtype=complex)"
        result = self.processor.compute_ifft(input_array)
        self.assertEqual(result, "(4,)") 

    def test_ifft_invalid_input_real(self):
        input_array = "np.array([1.0, 2.0, 3.0, 4.0])"
        with self.assertRaises(ValueError): 
            self.processor.compute_ifft(input_array)

    def test_ifft_invalid_input_string(self):
        input_array = "np.array([\"a\", \"b\", \"c\", \"d\"])"  
        with self.assertRaises(ValueError): 
            self.processor.compute_ifft(input_array)

    def test_ifft_large_input(self):
        input_array = "np.random.random(1000) + 1j * np.random.random(1000)"
        result = self.processor.compute_ifft(input_array)
        self.assertEqual(result, "(1000,)")

if __name__ == "__main__":
    unittest.main()
