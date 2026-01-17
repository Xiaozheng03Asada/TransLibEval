import unittest
from function_bitarray_tobytes import ConvertToBytesFunction

class TestConvertToBytesFunction(unittest.TestCase):

    def setUp(self):
        self.convert_to_bytes_func = ConvertToBytesFunction()

    def test_convert_standard_bitarray(self):
        ba = '110101' 
        result = self.convert_to_bytes_func.convert_to_bytes(ba)
        self.assertEqual(result, "b'\\xd4'")

    def test_convert_empty_bitarray(self):
        ba = ''
        result = self.convert_to_bytes_func.convert_to_bytes(ba)
        self.assertEqual(result, "b''") 

    def test_convert_padded_bitarray(self):
        ba = '1101'  
        result = self.convert_to_bytes_func.convert_to_bytes(ba)
        self.assertEqual(result, "b'\\xd0'") 

    def test_convert_bitarray_with_trailing_zeros(self):
        ba = '1101000'  
        result = self.convert_to_bytes_func.convert_to_bytes(ba)
        self.assertEqual(result, "b'\\xd0'")

    def test_non_bitarray_input(self):
        with self.assertRaises(TypeError):
            self.convert_to_bytes_func.convert_to_bytes("10102")  

if __name__ == "__main__":
    unittest.main()
