import unittest
from function_patsy_ModelDesc_from_formula import PatsyParser

class TestPatsyParser(unittest.TestCase):

    def setUp(self):
        self.parser = PatsyParser()

    def test_simple_formula(self):
        formula = "y ~ x1 + x2"
        result = self.parser.parse_formula(formula)
        self.assertIn('"lhs_terms": 1', result)
        self.assertIn('"rhs_terms": 3', result)

    def test_formula_with_no_response(self):
        formula = "~ x1 + x2"
        result = self.parser.parse_formula(formula)
        self.assertIn('"lhs_terms": 0', result)
        self.assertIn('"rhs_terms": 3', result)

    def test_invalid_formula(self):
        formula = "y ~ x1 +"
        result = self.parser.parse_formula(formula)
        self.assertEqual(result, "Error: Failed to parse formula.")

    def test_non_string_input(self):
        formula = 12345
        result = self.parser.parse_formula(formula)
        self.assertEqual(result, "Error: Formula must be a string.")

    def test_formula_no_intercept(self):
        formula = "y ~ x1 + x2 - 1"
        result = self.parser.parse_formula(formula)
        self.assertIn('"rhs_terms": 2', result)

if __name__ == "__main__":
    unittest.main()
