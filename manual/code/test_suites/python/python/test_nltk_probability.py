import unittest
from function_nltk_probability import ProbabilityExample

class TestProbabilityExample(unittest.TestCase):

    def test_compute_frequency(self):
        calc = ProbabilityExample()
        result = calc.compute_frequency("apple")
        self.assertEqual(result, "a:1, p:2, l:1, e:1")

    def test_compute_frequency_with_repeated_chars(self):
        calc = ProbabilityExample()
        result = calc.compute_frequency("banana")
        self.assertEqual(result, "b:1, a:3, n:2")

    def test_empty_input(self):
        calc = ProbabilityExample()
        result = calc.compute_frequency("")
        self.assertEqual(result, "")  # No characters, so an empty string

    def test_non_string_input(self):
        calc = ProbabilityExample()
        with self.assertRaises(TypeError):
            calc.compute_frequency(123)  # Invalid input type

    def test_edge_case(self):
        calc = ProbabilityExample()
        result = calc.compute_frequency("aabbcc")
        self.assertEqual(result, "a:2, b:2, c:2")

if __name__ == '__main__':
    unittest.main()
