from pyphonetics import Metaphone

class PhoneticProcessor:
    def generate_phonetics(self, word):
        metaphone = Metaphone()
        if word == '':
            return 'The given string is empty.'
        return metaphone.phonetics(word)