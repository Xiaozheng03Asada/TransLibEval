from decimal import Decimal

class GetDecimalTuple:
    def check_discount_for_large_order(self, value: str) -> str:
        try:
            decimal_value = Decimal(value)
            sign, digits, exponent = decimal_value.as_tuple()
            digits_str = ''.join(map(str, digits))
            return f"{sign},{digits_str},{exponent}"
        except Exception:
            return "Error"
