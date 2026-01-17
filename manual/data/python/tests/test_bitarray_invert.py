import unittest
from function_bitarray_invert import InvertBitsFunction

class TestInvertBitsFunction(unittest.TestCase):

    def setUp(self):
        self.invert_bits_func = InvertBitsFunction()

    def test_invert_all_bits(self):
        result = self.invert_bits_func.invert_bitarray("101010")
        self.assertEqual(result, "010101")

    def test_invert_partial_bits(self):
        result = self.invert_bits_func.invert_bitarray("111000", 1, 4)
        self.assertEqual(result, "100100")

    def test_invert_second_half(self):
        result = self.invert_bits_func.invert_bitarray("110011", 3, 6)
        self.assertEqual(result, "110100")

    def test_invert_large_alternating_pattern(self):
        bits = "01" * 5000
        result = self.invert_bits_func.invert_bitarray(bits)
        self.assertEqual(result, "10" * 5000)

    def test_invert_non_integer_indices(self):
        with self.assertRaises(TypeError):
            self.invert_bits_func.invert_bitarray("101010", 1.5, 4)

if __name__ == "__main__":
    unittest.main()
