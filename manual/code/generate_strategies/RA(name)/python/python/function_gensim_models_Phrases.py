from gensim.models import Phrases
from gensim.models.phrases import Phraser

class PhraseModelTrainer:
    def train_phrase_model(self, sentences_str: str, min_count: int, threshold: float) -> str:
        try:
            sentences = [sentence.split() for sentence in sentences_str.split(';') if sentence]
            phrases = Phrases(sentences, min_count=min_count, threshold=threshold)
            phrase_model = Phraser(phrases)
            
            detected_phrases = set()
            for sentence in sentences:
                transformed = phrase_model[sentence]
                detected_phrases.update(set(transformed) - set(sentence))
                
            return " | ".join(sorted(detected_phrases)) if detected_phrases else "No phrases detected"
        except Exception as e:
            return f"Error: {e}"
