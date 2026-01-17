import unittest
from function_jsonschema_validate import JSONValidator

class TestJSONValidator(unittest.TestCase):
    
    def setUp(self):
        self.validator = JSONValidator()

    def test_case_1(self):
        data = '{"name": "John", "age": 30}'
        schema = '{"type": "object", "properties": {"name": {"type": "string"}, "age": {"type": "integer"}}, "required": ["name", "age"]}'
        self.assertEqual(self.validator.validate_json(data, schema), "True")

    def test_case_2(self):
        data = '{"name": "John"}'
        schema = '{"type": "object", "properties": {"name": {"type": "string"}, "age": {"type": "integer"}}, "required": ["name", "age"]}'
        self.assertEqual(self.validator.validate_json(data, schema), "False")

    def test_case_3(self):
        data = '{"name": "John", "age": "30"}'
        schema = '{"type": "object", "properties": {"name": {"type": "string"}, "age": {"type": "integer"}}, "required": ["name", "age"]}'
        self.assertEqual(self.validator.validate_json(data, schema), "False")

    def test_case_4(self):
        data = '{}'
        schema = '{"type": "object", "properties": {"name": {"type": "string"}, "age": {"type": "integer"}}, "required": ["name", "age"]}'
        self.assertEqual(self.validator.validate_json(data, schema), "False")

    def test_case_5(self):
        data = '{"name": "John", "age": 30}'
        schema = '{"type": "object", "properties": {"name": {"type": "string"}}, "required": ["name"]}'
        self.assertEqual(self.validator.validate_json(data, schema), "True")

if __name__ == '__main__':
    unittest.main()
