import unittest
from function_bitarray_count import CountBitsFunction

class TestCountBitsFunction(unittest.TestCase):

    def setUp(self):
        self.count_bits_func = CountBitsFunction()

    def test_count_bits_random_pattern(self):
        ba = '1010101111001101' 
        self.assertEqual(self.count_bits_func.count_bits(ba, 1), 10)
        self.assertEqual(self.count_bits_func.count_bits(ba, 0), 6)

    def test_count_all(self):
        ba = '1111111'  
        self.assertEqual(self.count_bits_func.count_bits(ba, 1), 7)

        ba = '0000000'  
        self.assertEqual(self.count_bits_func.count_bits(ba, 0), 7)

    def test_invalid_value(self):
        ba = '1100'  
        with self.assertRaises(ValueError):
            self.count_bits_func.count_bits(ba, 2)

    def test_non_string_input(self):
        with self.assertRaises(TypeError):
            self.count_bits_func.count_bits([1, 0, 1], 1) 

    def test_count_bits_subrange(self):
        ba = '101010' * 1000  
        sub_ba = ba[1000:2000] 
        self.assertEqual(self.count_bits_func.count_bits(sub_ba, 1), 500)
        self.assertEqual(self.count_bits_func.count_bits(sub_ba, 0), 500)

if __name__ == "__main__":
    unittest.main()
