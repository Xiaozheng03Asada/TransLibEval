from joblib import hashing

class HashingExample:
    def compute_hash(self, input_data) -> str:
        return str(hashing.hash(input_data))
