from boltons.cacheutils import LRU


class LRUCacheManager:
    def manage_cache_operations(self, cache_size, operations_str):
        cache = LRU(cache_size)
        results = []
        operations = operations_str.split(',')
        if operations_str == '':
            return ''
        i = 0
        while i < len(operations):
            operation_type = operations[i]
            key = operations[i + 1]

            if operation_type == "set":
                value = operations[i + 2]
                cache[key] = value
                i += 3
            elif operation_type == "get":
                result = cache.get(key)
                results.append(str(result) if result is not None else "None")
                i += 2
            else:
                raise ValueError("Invalid operation type")

        return ",".join(results)
