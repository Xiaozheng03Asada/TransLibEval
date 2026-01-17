from sortedcontainers import SortedDict

class SortedDictHandler:
    def modify_sorted_dict(self, index: int) -> str:
        sorted_dict = SortedDict({3: "three", 1: "one", 5: "five"})
        try:
            removed_item = sorted_dict.popitem(index)
            return f"{dict(sorted_dict)} {removed_item}"
        except IndexError:
            return "error: Invalid index"
