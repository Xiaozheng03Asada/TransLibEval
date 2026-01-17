import unittest
from function_nltk_stem_PorterStemmer import Stemmer

class TestPorterStemmer(unittest.TestCase):

    def test_stem_running(self):
        stemmer = Stemmer()  # 实例化 Stemmer 类
        result = stemmer.test_stem('running')  # 调用类方法
        self.assertEqual(result, 'run')

    def test_stem_played(self):
        stemmer = Stemmer()
        result = stemmer.test_stem('played')
        self.assertEqual(result, 'play')

    def test_stem_dogs(self):
        stemmer = Stemmer()
        result = stemmer.test_stem('dogs')
        self.assertEqual(result, 'dog')

    def test_stem_empty_string(self):
        stemmer = Stemmer()
        result = stemmer.test_stem('')
        self.assertEqual(result, '')

    def test_stem_word_with_special_characters(self):
        stemmer = Stemmer()
        result = stemmer.test_stem('$#@!word')
        self.assertEqual(result, '$#@!word')


if __name__ == '__main__':
    unittest.main()