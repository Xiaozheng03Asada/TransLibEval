from funcy import flatten

class ListProcessor:
    def process_list(self, elements: str) -> str:
        try:
            parsed_elements = eval(elements)
            if not isinstance(parsed_elements, (list, tuple, set)):
                return "Error: input is not iterable"
            return str(list(flatten(parsed_elements)))
        except Exception:
            return "Error: invalid input"
