from nltk.stem import PorterStemmer

class Stemmer:
    def test_stem(self, word):
        porter_stemmer = PorterStemmer()
        return porter_stemmer.stem(word)