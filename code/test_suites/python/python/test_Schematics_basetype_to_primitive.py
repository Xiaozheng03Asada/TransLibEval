import unittest
from function_Schematics_basetype_to_primitive import IntegerListType

class TestIntegerListType(unittest.TestCase):

    def test_valid_integer_list(self):
        field = IntegerListType()
        input_value = "[1, 2, 3]"
        self.assertEqual(field.to_primitive(input_value), "[1, 2, 3]")

    def test_invalid_non_list_input(self):
        field = IntegerListType()
        with self.assertRaises(ValueError) as context:
            field.to_primitive("not_a_list")
        self.assertIn("Value must be a string representing a list.", str(context.exception))

    def test_empty_list(self):
        field = IntegerListType()
        input_value = "[]"
        self.assertEqual(field.to_primitive(input_value), "[]")

    def test_single_integer(self):
        field = IntegerListType()
        input_value = "[5]"
        self.assertEqual(field.to_primitive(input_value), "[5]")

    def test_empty_list_string(self):
        field = IntegerListType()
        input_value = "[]"
        self.assertEqual(field.to_primitive(input_value), "[]")


if __name__ == "__main__":
    unittest.main()
