import spacy

class SpacyVectorProcessor:
    def vector_lengths(self, text: str) -> str:
        nlp = spacy.load('en_core_web_sm')
        doc = nlp(text)

        vector_lengths = ""
        for token in doc:
            vector_length = sum(value ** 2 for value in token.vector) ** 0.5
            vector_lengths += f"{token.text}: {vector_length:.6f}, "

        if vector_lengths:
            vector_lengths = vector_lengths[:-2]

        return vector_lengths
