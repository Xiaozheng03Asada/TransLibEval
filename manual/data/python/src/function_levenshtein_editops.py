import Levenshtein

class StringProcessor:
    def calculate_editops(self, str1, str2):
        if not isinstance(str1, str) or not isinstance(str2, str):
            return "Error: Both inputs must be strings"
        edit_operations = Levenshtein.editops(str1, str2)
        return str(edit_operations)
