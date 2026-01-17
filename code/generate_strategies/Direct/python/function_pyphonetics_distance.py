from pyphonetics import RefinedSoundex

class SoundexProcessor:
    def compute_distance(self, word1, word2, metric):
        rs = RefinedSoundex()
        if metric == 'refined':
            return rs.distance(word1, word2)
        elif metric == 'hamming':
            return rs.distance(word1, word2, metric='hamming')
        else:
            return "Invalid metric. Only 'refined' and 'hamming' are supported."