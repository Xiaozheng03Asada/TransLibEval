from algorithms.heap import heapify

class HeapifyFunction:
    def heapify_from_string(self, arr_str):
        arr = list(map(int, arr_str.split(',')))
        heapify(arr)
        return ','.join(map(str, arr))
