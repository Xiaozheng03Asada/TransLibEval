from Crypto.Cipher import AES
import base64
import os

class AESCipher:
    def process(key: str, text: str, mode: str = 'encrypt') -> str:
        key = key.ljust(32)[:32] 
        if mode == 'encrypt':
            block_size = AES.block_size
            padding_length = block_size - (len(text) % block_size)
            padded_text = text + (chr(padding_length) * padding_length)

            iv = os.urandom(AES.block_size)  
            cipher = AES.new(key.encode('utf-8'), AES.MODE_CBC, iv)
            ciphertext = cipher.encrypt(padded_text.encode('utf-8'))
            return base64.b64encode(iv + ciphertext).decode('utf-8')

        elif mode == 'decrypt':
            encrypted_text = base64.b64decode(text)
            iv = encrypted_text[:AES.block_size] 
            cipher = AES.new(key.encode('utf-8'), AES.MODE_CBC, iv)
            decrypted_text = cipher.decrypt(encrypted_text[AES.block_size:]).decode('utf-8')

            padding_length = ord(decrypted_text[-1])
            if padding_length > AES.block_size:
                raise ValueError("Invalid padding")
            unpadded_text = decrypted_text[:-padding_length]
            return unpadded_text

        else:
            raise ValueError("Mode must be 'encrypt' or 'decrypt'")
