import unittest
from function_gensim_train import Word2VecModelTrainer

class TestTrainWord2VecModel(unittest.TestCase):

    def setUp(self):
        self.trainer = Word2VecModelTrainer()

    def test_train_word2vec_model_valid(self):
        result = self.trainer.train_word2vec_model("dog barks at cat;cat meows at dog", 50, 2, 1)
        self.assertIn("dog", result)
        self.assertIn("cat", result)

    def test_train_word2vec_model_invalid_data(self):
        result = self.trainer.train_word2vec_model("", 50, 2, 1)
        self.assertEqual(result, "Training failed")

    def test_train_word2vec_model_min_count(self):
        result = self.trainer.train_word2vec_model("dog barks;cat meows;dog and cat", 50, 2, 2)
        self.assertNotIn("barks", result)
        self.assertNotIn("meows", result)

    def test_train_word2vec_model_custom_params(self):
        result = self.trainer.train_word2vec_model("dog barks at cat;cat meows at dog", 100, 3, 1)
        self.assertIn("dog", result)
        self.assertIn("cat", result)

    def test_train_word2vec_model_with_special_characters(self):
        result = self.trainer.train_word2vec_model("dog barks;cat@home meows;hello! world", 50, 2, 1)
        self.assertIn("cat@home", result)
        self.assertIn("hello!", result)

if __name__ == '__main__':
    unittest.main()
