import unittest
from function_patsy_Term import PatsyTerm

class TestGenerateAndConvertTerm(unittest.TestCase):

    def setUp(self):
        self.term = PatsyTerm()

    def test_generate_and_convert_term(self):
        term_str = self.term.generate_and_convert_term('x1 + x2 + x3')
        self.assertEqual(term_str, 'x 1  + 2 3')

    def test_empty_formula(self):
        term_str = self.term.generate_and_convert_term('')
        self.assertEqual(term_str, '')

    def test_complex_formula_with_mixed_interactions(self):
        term_str_from_term = self.term.generate_and_convert_term('x1 * x2 + x3 / x4 - x5 + x6')
        self.assertEqual(term_str_from_term, 'x 1  * 2+ 3/ 4- 5 6')

    def test_formula_with_interaction(self):
        term_str_from_term = self.term.generate_and_convert_term('x1 * x2 + x3')
        self.assertEqual(term_str_from_term, 'x 1  * 2+ 3')

    def test_multiple_interaction_terms(self):
        term_str_from_term = self.term.generate_and_convert_term('x1 * x2 + x3 / x4 + x5')
        self.assertEqual(term_str_from_term, 'x 1  * 2+ 3/ 4 5')

if __name__ == "__main__":
    unittest.main()
