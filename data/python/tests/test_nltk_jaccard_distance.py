import unittest
from function_nltk_jaccard_distance import JaccardExample

class TestJaccardExample(unittest.TestCase):

    def test_compute_jaccard_distance_identical_strings(self):
        calc = JaccardExample()
        result = calc.compute_jaccard_distance("apple", "apple")
        self.assertEqual(result, 0.0)

    def test_compute_jaccard_distance_different_strings(self):
        calc = JaccardExample()
        result = calc.compute_jaccard_distance("apple", "orange")
        self.assertGreater(result, 0.0)  # The distance should be positive

    def test_compute_jaccard_distance_empty_strings(self):
        calc = JaccardExample()
        result = calc.compute_jaccard_distance("", "")
        self.assertEqual(result, 0.0)  # Identical empty strings should have distance 0

    def test_compute_jaccard_distance_partial_overlap(self):
        calc = JaccardExample()
        result = calc.compute_jaccard_distance("apple", "app")
        self.assertGreater(result, 0.0)  # There is some overlap, but not identical

    def test_non_string_input(self):
        calc = JaccardExample()
        with self.assertRaises(TypeError):
            calc.compute_jaccard_distance("apple", 123)

if __name__ == '__main__':
    unittest.main()
