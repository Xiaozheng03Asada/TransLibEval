from collections import Counter

class WordCounter:
    def count_words(self, word_string):
        if not isinstance(word_string, str):
            raise TypeError("Input should be a string of words")

        if not word_string.strip():
            return ""

        words = word_string.split(",")
        word_count = Counter(word.strip().lower() for word in words)

        result_str = ""
        for word, count in word_count.items():
            result_str += f"{word}:{count};"

        return result_str.strip(';')