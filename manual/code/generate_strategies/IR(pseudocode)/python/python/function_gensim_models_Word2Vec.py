from gensim.models import Word2Vec

class Word2VecModelTrainer:
    def train_word2vec_model(self, sentences_str: str) -> str:
        try:
            sentences = [sentence.split() for sentence in sentences_str.split(";") if sentence]
            if not sentences:
                return "Training failed"

            model = Word2Vec(sentences, vector_size=50, window=3, min_count=1, workers=4)
            words = list(model.wv.index_to_key)
            return "|".join(words)
        except Exception:
            return "Training failed"
