from itertools import permutations
from collections import defaultdict


class PermutationsProcessor:

    def get_permutations(self, data):
        if not isinstance(data, str):
            raise ValueError("Input data must be a string.")

        # 获取所有排列，并对排列进行排序，确保顺序一致
        perms = [''.join(p) for p in sorted(permutations(data))]

        # 统计排列频率
        perm_dict = defaultdict(int)
        for perm in perms:
            perm_dict[perm] += 1

        # 将排列和频率字典转换为字符串
        perms_str = ', '.join(perms)
        perm_dict_str = ', '.join(f"{key}: {value}" for key, value in perm_dict.items())

        return perms_str + "\n" + perm_dict_str
