import Levenshtein

class StringProcessor:
    def calculate_setratio(self, str1, str2):
        return Levenshtein.setratio(str1, str2)
