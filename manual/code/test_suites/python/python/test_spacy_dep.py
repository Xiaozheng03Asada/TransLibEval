import unittest
from function_spacy_dep import DependencyParser 

class TestGetVectorLengths(unittest.TestCase):

    def test_get_pos_tags_empty_text(self):
        parser = DependencyParser()
        result = parser.test_dep("")
        self.assertEqual(type(result), str)

    def test_subject_verb_object_sentence(self):
        parser = DependencyParser()
        text = "I love flowers"
        result = parser.test_dep(text)
        self.assertEqual(result, "I (nsubj), love (ROOT), flowers (dobj)")

    def test_new_complex_sentence(self):
        parser = DependencyParser()
        text = "The quick brown fox jumped over the lazy dog"
        result = parser.test_dep(text)
        expected_result = "The (det), quick (amod), brown (amod), fox (nsubj), jumped (ROOT), over (prep), the (det), lazy (amod), dog (pobj)"
        self.assertEqual(result, expected_result)

    def test_word_different_dependencies(self):
        parser = DependencyParser()
        result1 = parser.test_dep("The dog barked loudly.")
        result2 = parser.test_dep("I saw the dog with a collar.")
        self.assertIn("dog (nsubj)", result1)
        self.assertIn("dog (dobj)", result2)

    def test_get_pos_tags_non_string_input(self):
        parser = DependencyParser()
        with self.assertRaises(TypeError):
            parser.test_dep(123)

if __name__ == '__main__': 
    unittest.main()
