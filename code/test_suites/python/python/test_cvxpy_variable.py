import unittest
from function_cvxpy_variable import CVXPYVariableFunction

class TestCVXPYVariableFunction(unittest.TestCase):

    def test_variable_value_assignment(self):
        func = CVXPYVariableFunction()
        result = func.process_variables("5", "10")
        self.assertEqual(result, "x: 5.0, y: 10.0")

        result = func.process_variables("0", "0")
        self.assertEqual(result, "x: 0.0, y: 0.0")

    def test_unassigned_variable(self):
        func = CVXPYVariableFunction()
        result = func.process_variables()
        self.assertTrue(isinstance(result[0], str))
        self.assertTrue(isinstance(result[1], str))

    def test_variable_negative_size(self):
        func = CVXPYVariableFunction()
        result = func.process_variables("-1", "10")
        self.assertEqual(result, "Error: Variable size cannot be negative")

    def test_invalid_variable_constraint(self):
        func = CVXPYVariableFunction()
        result = func.process_variables(constraint="string_value")
        self.assertEqual(result, "Error: Invalid variable constraint")

    def test_invalid_input(self):
        func = CVXPYVariableFunction()
        result = func.process_variables(x_value="abc", y_value="xyz")
        self.assertEqual(result, "Error: Invalid value for x or y")

if __name__ == "__main__":
    unittest.main()
