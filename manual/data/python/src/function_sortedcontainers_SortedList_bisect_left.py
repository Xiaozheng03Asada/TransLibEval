from sortedcontainers import SortedList

class HanSeok:
    def find_insert_position(self, value: int = 0, sorted_list: str = None) -> int:
        if sorted_list is None:
            sorted_list = "1,3,5,8"
        sorted_list = sorted_list.split(",")
        sorted_list = [int(x) for x in sorted_list]
        sorted_list = SortedList(sorted_list)
        position = sorted_list.bisect_left(value)
        return position
