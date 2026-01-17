from datetime import datetime

class DateTimeModifier:

    def get_current_datetime(self, date_string: str = None) -> str:
        if date_string:
            try:
                return datetime.strptime(date_string, "%Y-%m-%d %H:%M:%S").strftime("%Y-%m-%d %H:%M:%S")
            except ValueError:
                raise ValueError("The date string format is incorrect.")
        else:
            return "1900-01-01 00:00:00"
