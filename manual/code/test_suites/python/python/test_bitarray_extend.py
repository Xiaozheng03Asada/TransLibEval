
import unittest
from function_bitarray_extend import BitarrayExtender

class TestExtendBitsFunction(unittest.TestCase):

    def setUp(self):
        self.extender = BitarrayExtender()

    def test_extend_with_valid_bits(self):
        result = self.extender.extend_bits("101", "010")
        self.assertEqual(result, "101010")

    def test_invalid_input_non_binary(self):
        with self.assertRaises(ValueError):
            self.extender.extend_bits("101", "102")

    def test_non_string_input(self):
        with self.assertRaises(TypeError):
            self.extender.extend_bits(101, "010")

    def test_extend_with_empty_string(self):
        result = self.extender.extend_bits("101", "")
        self.assertEqual(result, "101") 

    def test_extend_empty_string_with_bits(self):
        result = self.extender.extend_bits("", "010")
        self.assertEqual(result, "010")  

if __name__ == "__main__":
    unittest.main()
