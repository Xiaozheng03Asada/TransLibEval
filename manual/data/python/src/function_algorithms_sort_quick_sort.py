from algorithms.sort.quick_sort import quick_sort

class QuickSortFunction:
    def quick_sort_from_string(self, input_str):
        if not input_str:
            return ""
        input_list = list(map(int, input_str.split(',')))
        sorted_list = quick_sort(input_list)
        return ','.join(map(str, sorted_list))
