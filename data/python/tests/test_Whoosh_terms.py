import unittest
import shutil
import os
from function_Whoosh_terms import WhooshTerms

class TestWhooshListTerms(unittest.TestCase):

    def setUp(self):
        self.index_dir = "test_list_terms_index"
        self.func = WhooshTerms()
        self.func.manage_index_and_list_terms(self.index_dir)

    def tearDown(self):
        if os.path.exists(self.index_dir):
            shutil.rmtree(self.index_dir, ignore_errors=True)

    def test_list_terms(self):
        terms = self.func.manage_index_and_list_terms(self.index_dir)
        self.assertIn("hello", terms)
        self.assertIn("test", terms)
        self.assertTrue(len(terms) > 0)

    def test_invalid_index_directory(self):
        invalid_index_dir = "invalid_index"
        if os.path.exists(invalid_index_dir):
            shutil.rmtree(invalid_index_dir)
        with self.assertRaises(ValueError):
            self.func.manage_index_and_list_terms(invalid_index_dir, add_documents=False)
            
    def test_case_insensitivity(self):
        terms = self.func.manage_index_and_list_terms(self.index_dir)
        self.assertIn("whoosh", terms)
        self.assertNotIn("Whoosh", terms)

    def test_word_frequency(self):
        terms = self.func.manage_index_and_list_terms(self.index_dir)
        self.assertGreater(len(terms), 0)

    def test_term_existence(self):
        terms = self.func.manage_index_and_list_terms(self.index_dir)
        self.assertIn("whoosh", terms)
        self.assertNotIn("nonexistentword", terms)

if __name__ == '__main__':
    unittest.main()
