import unittest
from function_collections_defaultdict import WordCounter

class TestWordCounter(unittest.TestCase):

    def setUp(self):
        self.counter = WordCounter()

    def test_count_words_normal_case(self):
        word_string = "apple,banana,apple,cherry,banana,apple"
        result = self.counter.count_words(word_string)
        self.assertEqual(result, "apple:3;banana:2;cherry:1")

    def test_count_words_empty_string(self):
        word_string = ""
        result = self.counter.count_words(word_string)
        self.assertEqual(result, "")

    def test_count_words_with_non_string_elements(self):
        word_string = "apple,123,banana,apple,true,cherry,banana,apple"
        result = self.counter.count_words(word_string)
        self.assertEqual(result, "apple:3;123:1;banana:2;true:1;cherry:1")

    def test_count_words_case_sensitivity(self):
        word_string = "Apple,apple,Banana,banana"
        result = self.counter.count_words(word_string)
        self.assertEqual(result, "apple:2;banana:2")

    def test_count_words_error_handling(self):
        with self.assertRaises(TypeError):
            self.counter.count_words(123)  # 输入是整数

