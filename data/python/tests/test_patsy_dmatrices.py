import unittest
from function_patsy_dmatrices import PatsyProcessor

class TestPatsyProcessor(unittest.TestCase):

    def setUp(self):
        self.processor = PatsyProcessor()
        self.data = '''
        {
            "y": [1, 2, 3, 4],
            "x1": [2, 4, 6, 8],
            "x2": [1, 3, 5, 7],
            "category": ["A", "B", "A", "B"]
        }'''

    def test_simple_formula(self):
        formula = "y ~ x1 + x2"
        result = self.processor.process_formula(self.data, formula)
        self.assertIn('"y_shape": [4, 1]', result)
        self.assertIn('"X_shape": [4, 3]', result)

    def test_interaction_formula(self):
        formula = "y ~ x1 * x2"
        result = self.processor.process_formula(self.data, formula)
        self.assertIn('"X_shape": [4, 4]', result)

    def test_polynomial_formula(self):
        formula = "y ~ x1 + I(x1 ** 2) + I(x1 ** 3)"
        result = self.processor.process_formula(self.data, formula)
        self.assertIn('"X_shape": [4, 4]', result)

    def test_invalid_formula(self):
        formula = "y ~ non_existent_column"
        result = self.processor.process_formula(self.data, formula)
        self.assertEqual(result, "Error: invalid input")

    def test_malformed_json(self):
        malformed_data = '{"y": [1, 2, 3, 4], "x1": [2, 4, 6, 8], "x2": [1, 3, 5, 7],'
        formula = "y ~ x1 + x2"
        result = self.processor.process_formula(malformed_data, formula)
        self.assertEqual(result, "Error: invalid input")

if __name__ == '__main__':
    unittest.main()
