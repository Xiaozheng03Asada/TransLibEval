import unittest
from function_datetime_strptime import DateParser

class TestDateParser(unittest.TestCase):

    def test_valid_date(self):
        calc = DateParser()
        result = calc.parse_date("2023-03-25 14:30:00", "%Y-%m-%d %H:%M:%S")
        self.assertEqual(result, "2023-03-25 14:30:00")

    def test_invalid_date_format(self):
        calc = DateParser()
        result = calc.parse_date("25/03/2023", "%Y-%m-%d %H:%M:%S")
        self.assertEqual(result, "failed")

    def test_different_format(self):
        calc = DateParser()
        result = calc.parse_date("25/03/2023", "%d/%m/%Y")
        self.assertEqual(result, "2023-03-25 00:00:00")

    def test_empty_input(self):
        calc = DateParser()
        result = calc.parse_date("", "%Y-%m-%d %H:%M:%S")
        self.assertEqual(result, "failed")

    def test_non_date_input(self):
        calc = DateParser()
        result = calc.parse_date("invalid-date", "%Y-%m-%d %H:%M:%S")
        self.assertEqual(result, "failed")

if __name__ == '__main__':
    unittest.main()
