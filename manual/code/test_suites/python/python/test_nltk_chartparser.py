import unittest
from function_nltk_chartparser import TestChartParser

class TestChartParserTestCase(unittest.TestCase):

    def test_correct_sentence_parsing(self):
        sentence = "the dog chased the cat"
        parser = TestChartParser()
        result = parser.test_chartparser(sentence)
        self.assertTrue(len(result) > 0)  # 确保解析返回了某些内容

    def test_wrong_word_order(self):
        sentence = "chased the dog the cat"
        parser = TestChartParser()
        result = parser.test_chartparser(sentence)
        self.assertEqual(result, '')  # 错误的语序应该返回空字符串

    def test_missing_word(self):
        sentence = "the dog the cat"
        parser = TestChartParser()
        result = parser.test_chartparser(sentence)
        self.assertEqual(result, '')  # 缺失的词应返回空字符串

    def test_duplicate_nouns(self):
        sentence = "the dog the dog chased the cat"
        parser = TestChartParser()
        result = parser.test_chartparser(sentence)
        self.assertEqual(result, '')  # 重复的名词应返回空字符串

    def test_single_word_sentence(self):
        sentence = "dog"
        parser = TestChartParser()
        result = parser.test_chartparser(sentence)
        self.assertEqual(result, '')  # 单个词的句子应该返回空字符串

if __name__ == '__main__':
    unittest.main()