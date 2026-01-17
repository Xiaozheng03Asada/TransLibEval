import unittest
from function_gensim_models_Word2Vec import Word2VecModelTrainer

class TestWord2VecModel(unittest.TestCase):

    def setUp(self):
        self.trainer = Word2VecModelTrainer()

    def test_train_word2vec_model(self):
        result = self.trainer.train_word2vec_model("dog barks;cat meows;dog chases cat")
        self.assertTrue("dog" in result and "cat" in result)

    def test_similarity_words_exist(self):
        result = self.trainer.train_word2vec_model("apple orange;banana grape")
        self.assertIn("apple", result)
        self.assertIn("orange", result)

    def test_empty_input(self):
        result = self.trainer.train_word2vec_model("")
        self.assertEqual(result, "Training failed")

    def test_single_word_sentences(self):
        result = self.trainer.train_word2vec_model("hello;world;test")
        self.assertIn("hello", result)
        self.assertIn("world", result)
        self.assertIn("test", result)

    def test_large_sentence(self):
        sentence = " ".join([f"word{i}" for i in range(100)])
        result = self.trainer.train_word2vec_model(sentence)
        self.assertTrue("word99" in result and "word0" in result)

if __name__ == '__main__':
    unittest.main()
