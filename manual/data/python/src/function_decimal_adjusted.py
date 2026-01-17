from decimal import Decimal

class GetDecimalAdjusted:
    def check_discount_for_large_order(self, value):
        try:
            decimal_value = Decimal(str(value))

            if decimal_value == 0:
                return "-Infinity"

            return str(decimal_value.adjusted())
        except Exception:
            return "Error"
