import unittest
from function_gensim_models_Phrases import PhraseModelTrainer

class TestTrainPhraseModel(unittest.TestCase):
    
    def setUp(self):
        self.trainer = PhraseModelTrainer()

    def test_phrase_detection(self):
        result = self.trainer.train_phrase_model("new york is great;new york city is big;san francisco is beautiful;los angeles is sunny", 1, 5.0)
        self.assertIn("new_york", result)
    
    def test_no_short_phrases(self):
        result = self.trainer.train_phrase_model("new york is great;new york city is big;san francisco is beautiful;los angeles is sunny", 2, 20.0)
        self.assertNotIn("los_angeles", result)
    
    def test_high_threshold(self):
        result = self.trainer.train_phrase_model("new york is great;new york city is big;san francisco is beautiful;los angeles is sunny", 1, 50.0)
        self.assertNotIn("new_york", result)
    
    def test_single_sentence(self):
        result = self.trainer.train_phrase_model("this is a test case", 1, 1.0)
        self.assertEqual(result, "No phrases detected")
    
    def test_overlapping_phrases(self):
        result = self.trainer.train_phrase_model("new york city is new york;new york is big", 1, 1.0)
        self.assertIn("new_york", result)

if __name__ == '__main__':
    unittest.main()
