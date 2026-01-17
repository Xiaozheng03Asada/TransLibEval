from nltk.probability import FreqDist


class ProbabilityExample:
    def compute_frequency(self, data: str) -> str:
        freq_dist = FreqDist(data)

        return ', '.join(f"{key}:{value}" for key, value in freq_dist.items())
