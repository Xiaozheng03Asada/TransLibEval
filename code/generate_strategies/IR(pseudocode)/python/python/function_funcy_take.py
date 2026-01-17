from funcy import take

class IterableProcessor:
    def get_first_n_elements(self, iterable: str, n: int) -> str:
        try:
            parsed_iterable = eval(iterable)
            if not isinstance(parsed_iterable, (list, str)) or n < 0:
                return "Error: invalid input"
            return str(take(n, parsed_iterable))
        except Exception:
            return "Error: invalid input"
