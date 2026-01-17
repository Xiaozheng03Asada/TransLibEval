

# Test Cases
import unittest

from function_pyphonetics_distance import SoundexProcessor

class TestSoundexProcessor(unittest.TestCase):

    def setUp(self):
        self.processor = SoundexProcessor()

    def test_refined_soundex_distance_equal(self):
        result = self.processor.compute_distance('Rupert', 'Robert', 'refined')
        self.assertEqual(result, 0)

    def test_refined_soundex_distance_not_equal(self):
        result = self.processor.compute_distance('assign', 'assist', 'refined')
        self.assertEqual(result, 2)

    def test_hamming_distance_equal(self):
        result = self.processor.compute_distance('hello', 'hxllo', 'hamming')
        self.assertEqual(result, 1)

    def test_hamming_distance_not_equal(self):
        result = self.processor.compute_distance('katiang', 'sitting', 'hamming')
        self.assertEqual(result, 1)

    def test_invalid_metric(self):
        result = self.processor.compute_distance('hello', 'hxllo', 'invalid')
        self.assertEqual(result, "Invalid metric. Only 'refined' and 'hamming' are supported.")
if __name__ == "__main__":
    unittest.main()
