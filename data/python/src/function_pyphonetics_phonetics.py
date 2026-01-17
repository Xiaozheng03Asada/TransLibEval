from pyphonetics import Soundex

class SoundexProcessor:
    def generate_soundex(self, input_string):
        soundex = Soundex()
        if input_string == '':
            return "The given string is empty."
        return soundex.phonetics(input_string)
