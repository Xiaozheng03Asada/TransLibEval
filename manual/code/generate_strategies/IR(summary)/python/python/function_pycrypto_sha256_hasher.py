from Crypto.Hash import SHA256

class SHA256Hasher:
    def hash_text(self, text: str) -> str:
        sha256_hash = SHA256.new()
        sha256_hash.update(text.encode('utf-8'))
        return sha256_hash.hexdigest()
