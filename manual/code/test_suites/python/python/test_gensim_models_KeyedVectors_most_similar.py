import unittest
from function_gensim_models_KeyedVectors_most_similar import FindMostSimilarWords

class TestFindMostSimilarWords(unittest.TestCase):

    def setUp(self):
        self.find_similar = FindMostSimilarWords()

    def test_find_most_similar_words_valid(self):
        result = self.find_similar.find_most_similar_words('king', 3)
        self.assertIn("queen: 1.00", result)
        self.assertIn("man: 1.00", result)

    def test_find_most_similar_words_invalid(self):
        result = self.find_similar.find_most_similar_words('nonexistent', 3)
        self.assertEqual(result, "Error")

    def test_find_most_similar_words_topn(self):
        result = self.find_similar.find_most_similar_words('king', 2)
        self.assertEqual(len(result.split(", ")), 2)

    def test_find_most_similar_words_edge_case(self):
        result = self.find_similar.find_most_similar_words('not_in_vocab', 3)
        self.assertEqual(result, "Error")

    def test_find_most_similar_words_empty_model(self):
        empty_model = FindMostSimilarWords()
        empty_model.word_vectors = {} 
        result = empty_model.find_most_similar_words('king', 3)
        self.assertEqual(result, "queen: 1.00, man: 1.00, woman: 1.00")

if __name__ == '__main__':
    unittest.main()
