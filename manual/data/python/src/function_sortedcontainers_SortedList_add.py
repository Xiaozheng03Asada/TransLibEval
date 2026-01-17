from sortedcontainers import SortedList

class SortedListHandler:
    def modify_sorted_list(self, index: int) -> str:
        sorted_list = SortedList()
        sorted_list.add(5)
        sorted_list.add(3)
        sorted_list.add(8)
        sorted_list.add(1)
        try:
            removed_item = sorted_list.pop(index)
            return f"Removed item: {removed_item}, Remaining list: {list(sorted_list)}"
        except IndexError:
            return "Index out of range"
            
