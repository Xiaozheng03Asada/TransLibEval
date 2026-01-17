from algorithms.search.linear_search import linear_search

class LinearSearchFunction:
    def linear_search_from_string(self, arr, target):
        if not arr:
            return -1
        arr = list(map(int, arr.split(',')))
        target = int(target)
        return linear_search(arr, target)
