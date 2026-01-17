from funcy import distinct

class ListProcessor:
    def process_list(self, input_str: str) -> str:
        elements = input_str.split(",")
        unique_elements = list(distinct(elements))
        return ",".join(unique_elements)
