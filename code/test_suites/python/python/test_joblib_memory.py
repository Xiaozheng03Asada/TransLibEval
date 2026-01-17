import unittest
from function_joblib_memory import MemoryExample

class TestMemoryExample(unittest.TestCase):

    def test_compute_square(self):
        calc = MemoryExample()
        result = calc.compute_square(4)
        self.assertEqual(result, 16)

    def test_compute_square_another(self):
        calc = MemoryExample()
        result = calc.compute_square(5)
        self.assertEqual(result, 25)

    def test_compute_square_cache(self):
        calc = MemoryExample()
        result1 = calc.compute_square(4)
        result2 = calc.compute_square(4)
        self.assertEqual(result1, result2)  # Cached result

    def test_compute_square_zero(self):
        calc = MemoryExample()
        result = calc.compute_square(0)
        self.assertEqual(result, 0)

    def test_non_integer_input(self):
        calc = MemoryExample()
        with self.assertRaises(TypeError):
            calc.compute_square("string")

if __name__ == '__main__':
    unittest.main()
