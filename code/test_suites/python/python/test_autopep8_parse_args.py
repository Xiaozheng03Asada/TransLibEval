import unittest
from function_autopep8_parse_args import ArgumentParser

class TestArgumentParser(unittest.TestCase):

    def test_parse_args_basic(self):
        input_str = '-n Alice -a 30'
        parser = ArgumentParser()
        result = parser.parse_arguments(input_str)
        self.assertEqual(result, 'Name: Alice, Age: 30, City: Not provided')

    def test_parse_args_with_city(self):
        input_str = '-n Bob -a 25 -c New York'
        parser = ArgumentParser()
        result = parser.parse_arguments(input_str)
        self.assertEqual(result, 'Error: 2')

    def test_parse_args_without_city(self):
        input_str = '-n Charlie -a 40'
        parser = ArgumentParser()
        result = parser.parse_arguments(input_str)
        self.assertEqual(result, 'Name: Charlie, Age: 40, City: Not provided')

    def test_parse_args_missing_age(self):
        input_str = '-n Dave'
        parser = ArgumentParser()
        result = parser.parse_arguments(input_str)
        self.assertIn('Error:', result)

    def test_parse_args_missing_name(self):
        input_str = '-a 50'
        parser = ArgumentParser()
        result = parser.parse_arguments(input_str)
        self.assertIn('Error:', result)

if __name__ == '__main__':
    unittest.main()
