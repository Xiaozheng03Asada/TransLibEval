from nltk.util import ngrams
from nltk.metrics import jaccard_distance


class JaccardExample:
    def compute_jaccard_distance(self, string1: str, string2: str) -> float:
        set1 = set(ngrams(string1, 2))
        set2 = set(ngrams(string2, 2))

        if not set1 and not set2:
            return 0.0

        return jaccard_distance(set1, set2)
