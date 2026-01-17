from funcy import merge

class DictMerger:
    def combine_dicts(self, dict1: str, dict2: str) -> str:
        try:
            parsed_dict1 = eval(dict1)
            parsed_dict2 = eval(dict2)
            if not isinstance(parsed_dict1, dict) or not isinstance(parsed_dict2, dict):
                return "Error: input is not a dictionary"
            return str(merge(parsed_dict1, parsed_dict2))
        except Exception:
            return "Error: invalid input"
