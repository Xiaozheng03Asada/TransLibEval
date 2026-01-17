from itertools import permutations
from collections import defaultdict


class PermutationsProcessor:

    def get_permutations(self, data):
        if not isinstance(data, str):
            raise ValueError("Input data must be a string.")

        
        perms = [''.join(p) for p in sorted(permutations(data))]

        
        perm_dict = defaultdict(int)
        for perm in perms:
            perm_dict[perm] += 1

        
        perms_str = ', '.join(perms)
        perm_dict_str = ', '.join(f"{key}: {value}" for key, value in perm_dict.items())

        return perms_str + "\n" + perm_dict_str
