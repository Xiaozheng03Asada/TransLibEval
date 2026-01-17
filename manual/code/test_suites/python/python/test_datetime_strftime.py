import unittest
from function_datetime_strftime import TimeFormatter

class TestFormatCurrentTime(unittest.TestCase):
    def test_format_with_specific_time(self):
        time_formatter = TimeFormatter()
        expected_result = "2024-11-11 10:30:45"
        self.assertEqual(time_formatter.format_current_time(2024, 11, 11, 10, 30, 45), expected_result)

    def test_format_date_only(self):
        time_formatter = TimeFormatter()
        expected_result = "2023-01-01 00:00:00"
        self.assertEqual(time_formatter.format_current_time(2023, 1, 1, 0, 0, 0), expected_result)

    def test_format_midnight_time(self):
        time_formatter = TimeFormatter()
        expected_result = "2024-01-01 00:00:00"
        self.assertEqual(time_formatter.format_current_time(2024, 1, 1, 0, 0, 0), expected_result)

    def test_format_end_of_year(self):
        time_formatter = TimeFormatter()
        expected_result = "2023-12-31 23:59:59"
        self.assertEqual(time_formatter.format_current_time(2023, 12, 31, 23, 59, 59), expected_result)

    def test_format_leap_year(self):
        time_formatter = TimeFormatter()
        expected_result = "2024-02-29 12:00:00"
        self.assertEqual(time_formatter.format_current_time(2024, 2, 29, 12, 0, 0), expected_result)

if __name__ == '__main__':
    unittest.main()