import unittest
from function_Schematics_model_to_native import Person

class TestPersonModel(unittest.TestCase):

    def test_valid_model(self):
        person = Person()
        data = "John Doe,30"
        expected = "John Doe 30 Unknown"
        self.assertEqual(person.get_native_representation(data), expected)

    def test_missing_name(self):
        person = Person()
        data = "25"
        result = person.get_native_representation(data)
        self.assertEqual(result, "error: Invalid input format")

    def test_negative_age(self):
        person = Person()
        data = "John Doe,-5"
        result = person.get_native_representation(data)
        self.assertTrue(result.startswith("error"))

    def test_default_city(self):
        person = Person()
        data = "Jane Smith,22"
        result = person.get_native_representation(data)
        self.assertIn("Unknown", result)

    def test_all_fields_provided(self):
        person = Person()
        data = "Alice,40"
        expected = "Alice 40 Unknown"
        self.assertEqual(person.get_native_representation(data), expected)

if __name__ == "__main__":
    unittest.main()
