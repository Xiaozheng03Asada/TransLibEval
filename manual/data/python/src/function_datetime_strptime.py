from datetime import datetime

class DateParser:
    def parse_date(self, date_str: str, format_str: str) -> str:
        try:
            parsed_date = datetime.strptime(date_str, format_str)
            return parsed_date.strftime('%Y-%m-%d %H:%M:%S')
        except ValueError:
            return "failed"