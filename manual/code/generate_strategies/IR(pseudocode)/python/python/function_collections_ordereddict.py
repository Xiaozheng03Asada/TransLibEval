from collections import OrderedDict


class OrderedDictCalculator:
    def process_key_value_pairs(self, data: str) -> str:
        if not data:
            return "failed"

        ordered_dict = OrderedDict()

        items = data.split(",")
        for item in items:

            if item.strip():
                try:
                    key, value = item.split(":")
                    ordered_dict[key.strip()] = value.strip()
                except ValueError:
                    continue

        if not ordered_dict:
            return "failed"

        result = ', '.join(f"{key}:{value}" for key, value in ordered_dict.items())
        return result
