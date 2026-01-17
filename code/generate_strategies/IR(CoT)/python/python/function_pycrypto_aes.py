from Crypto.Cipher import AES
import base64
import os

class AESCipher:
    @staticmethod
    def process(text: str, key: str, mode: str = 'encrypt') -> str:
        key = key.ljust(32)[:32]
        block_size = AES.block_size

        def pad(text: str) -> str:
            padding_length = block_size - (len(text) % block_size)
            return text + (chr(padding_length) * padding_length)

        def unpad(text: str) -> str:
            padding_length = ord(text[-1])
            return text[:-padding_length]

        if mode == 'encrypt':
            iv = os.urandom(block_size) 
            cipher = AES.new(key.encode('utf-8'), AES.MODE_CBC, iv)
            ciphertext = cipher.encrypt(pad(text).encode('utf-8'))
            return base64.b64encode(iv + ciphertext).decode('utf-8')
        elif mode == 'decrypt':
            text = base64.b64decode(text)  
            iv = text[:block_size]  
            cipher = AES.new(key.encode('utf-8'), AES.MODE_CBC, iv)
            plaintext = unpad(cipher.decrypt(text[block_size:]).decode('utf-8'))
            return plaintext
        else:
            raise ValueError("Mode must be 'encrypt' or 'decrypt'")
