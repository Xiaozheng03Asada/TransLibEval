import Levenshtein

class StringProcessor:
    def calculate_hamming_distance(self, str1, str2):
        if not isinstance(str1, str) or not isinstance(str2, str):
            return "Error: Both inputs must be strings"
        if len(str1) != len(str2):
            return "Strings must be of the same length for Hamming distance."
        return Levenshtein.hamming(str1, str2)
