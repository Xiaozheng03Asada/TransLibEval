import unittest
from function_patsy_dmatrix import PatsyProcessor

class TestPatsyProcessor(unittest.TestCase):

    def setUp(self):
        self.processor = PatsyProcessor()
        self.data = '''
        {
            "x": [1, 2, 3, 4],
            "y": [2, 4, 6, 8],
            "z": [5, 6, 7, 8],
            "w": [10, 15, 20, 25]
        }'''

    def test_polynomial_formula(self):
        formula = "x + I(x ** 2)"
        result = self.processor.generate_matrix(self.data, formula)
        self.assertIn('"matrix_shape": [4, 3]', result)

    def test_categorical_variable(self):
        formula = "C(z)"
        result = self.processor.generate_matrix(self.data, formula)
        self.assertIn('"matrix_shape": [4,', result)

    def test_multiple_interaction_terms(self):
        formula = "x * z * w"
        result = self.processor.generate_matrix(self.data, formula)
        self.assertIn('"matrix_shape": [4, 8]', result)

    def test_conditional_expression(self):
        formula = "x + (z > 6)"
        result = self.processor.generate_matrix(self.data, formula)
        self.assertIn('"matrix_shape": [4, 3]', result)

    def test_dummy_variable_encoding(self):
        formula = "C(w > 15)"
        result = self.processor.generate_matrix(self.data, formula)
        self.assertIn('"matrix_shape": [4, 2]', result)

if __name__ == '__main__':
    unittest.main()
