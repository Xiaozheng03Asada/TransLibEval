import Levenshtein

class StringProcessor:
    def calculate_levenshtein_distance(self, str1, str2):
        return Levenshtein.distance(str1, str2)
