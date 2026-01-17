from decimal import Decimal, ROUND_HALF_UP, InvalidOperation

class SetDecimalPrecision:
    def check_discount_for_large_order(self, value: str, precision: int) -> str:
        try:
            decimal_value = Decimal(value)
            target_precision = Decimal(f'1e-{precision}')
            return str(decimal_value.quantize(target_precision, rounding=ROUND_HALF_UP))
        except (ValueError, InvalidOperation):
            return "Error"
