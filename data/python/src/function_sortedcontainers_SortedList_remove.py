from sortedcontainers import SortedList

class HanSeok:
    def remove_element_from_list(self, value: int = 0, input_list: str = None) -> str:
        if input_list is None:
            input_list = "5,3,8,1"
        input_list = input_list.split(",")
        input_list = [int(x) for x in input_list]
        sorted_list = SortedList(input_list)
        try:
            sorted_list.remove(value)
        except ValueError:
            return "Value not found"
        return ",".join(map(str, sorted_list))
