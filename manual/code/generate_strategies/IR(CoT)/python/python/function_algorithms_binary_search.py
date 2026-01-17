from algorithms.search import binary_search

class BinarySearchFunction:
    def binary_search_index(self, sorted_string, target):
        index = binary_search(list(sorted_string), target)
        return index if index is not None else -1
