import decimal

class InfiniteCheck:
    def check_discount_for_large_order(self, value: str) -> str:
        try:
            decimal_value = decimal.Decimal(value)
            return "True" if decimal_value.is_infinite() else "False"
        except Exception:
            return "Error"
