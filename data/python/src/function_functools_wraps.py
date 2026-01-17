from boltons.cacheutils import LRU


class LRUCacheManager:

    def manage_lru_cache(self, cache_size, operations):
        cache_size = int(cache_size)
        operations = [tuple(op.split(',')) for op in operations.split(';')]
        cache = LRU(cache_size)
        results = []

        for op in operations:
            if op[0] == "set":
                cache[op[1]] = op[2]
            elif op[0] == "get":
                results.append(str(cache.get(op[1])) if cache.get(op[1]) is not None else "None")

        return ','.join(results)
