from gensim.models import KeyedVectors

class FindMostSimilarWords:
    def find_most_similar_words(self, word: str, topn: int) -> str:
        word_vectors = {
            'king': [0.2, 0.3, 0.4, 0.1, 0.5, 0.7, 0.9, 0.6, 0.2, 0.1],
            'queen': [0.2, 0.3, 0.4, 0.1, 0.5, 0.6, 0.8, 0.6, 0.2, 0.1],
            'man': [0.1, 0.2, 0.3, 0.0, 0.4, 0.6, 0.7, 0.5, 0.1, 0.0],
            'woman': [0.1, 0.2, 0.3, 0.0, 0.4, 0.5, 0.6, 0.5, 0.1, 0.1],
            'apple': [0.3, 0.3, 0.5, 0.4, 0.2, 0.7, 0.9, 0.6, 0.1, 0.2]
        }

        if not word_vectors:
            return "Error"

        if word not in word_vectors:
            return "Error"
        
        similar_words = [(w, 1.0) for w in word_vectors if w != word][:topn]
        return ", ".join(f"{w}: {s:.2f}" for w, s in similar_words)
