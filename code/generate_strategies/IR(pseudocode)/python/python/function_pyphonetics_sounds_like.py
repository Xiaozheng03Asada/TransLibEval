from pyphonetics import Soundex

class SoundexProcessor:
    def compare_strings(self, str1, str2):
        soundex = Soundex()
        if str1 == '' or str2 == '':
            return "The given string is empty."
        return soundex.sounds_like(str1, str2)
