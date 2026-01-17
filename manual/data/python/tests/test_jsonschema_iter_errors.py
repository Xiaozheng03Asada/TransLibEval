import unittest
from function_jsonschema_iter_errors import JSONValidator

class TestJSONValidator(unittest.TestCase):
    
    def setUp(self):
        self.validator = JSONValidator()

    def test_case_1(self):
        data = '{"name": "John", "age": 30}'
        schema = '{"type": "object", "properties": {"name": {"type": "string"}, "age": {"type": "integer"}}, "required": ["name", "age"]}'
        self.assertEqual(self.validator.validate_json(data, schema), "JSON is valid")

    def test_case_2(self):
        data = '{"name": "John"}'
        schema = '{"type": "object", "properties": {"name": {"type": "string"}, "age": {"type": "integer"}}, "required": ["name", "age"]}'
        self.assertEqual(self.validator.validate_json(data, schema), "Validation failed: 'age' is a required property")

    def test_case_3(self):
        data = '{"name": "John", "age": "30"}'
        schema = '{"type": "object", "properties": {"name": {"type": "string"}, "age": {"type": "integer"}}, "required": ["name", "age"]}'
        self.assertEqual(self.validator.validate_json(data, schema), "Validation failed: '30' is not of type 'integer'")

    def test_case_4(self):
        data = '{}'
        schema = '{"type": "object", "properties": {"name": {"type": "string"}, "age": {"type": "integer"}}, "required": ["name", "age"]}'
        self.assertEqual(self.validator.validate_json(data, schema), "Validation failed: 'name' is a required property")

    def test_case_5(self):
        data = '{"name": "John", "age": 30}'
        schema = '{"type": "object", "properties": {"name": {"type": "string"}}, "required": ["name"]}'
        self.assertEqual(self.validator.validate_json(data, schema), "JSON is valid")

if __name__ == '__main__':
    unittest.main()
