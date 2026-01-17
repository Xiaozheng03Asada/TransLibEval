from Crypto.PublicKey import RSA
from Crypto.Cipher import PKCS1_OAEP
import base64

class RSACrypto:
    def rsa_process(self, text: str = None, mode: str = 'encrypt', key_size: int = 2048, public_key_str: str = None, private_key_str: str = None) -> str:
        if mode == 'generate_keypair':
            key = RSA.generate(key_size)
            private_key = key.export_key().decode('utf-8')
            public_key = key.publickey().export_key().decode('utf-8')
            return private_key, public_key

        elif mode == 'encrypt':
            if text is None or public_key_str is None:
                raise ValueError("Text and public key must be provided for encryption")
            public_key = RSA.import_key(public_key_str.encode('utf-8'))
            cipher = PKCS1_OAEP.new(public_key)
            encrypted_text = cipher.encrypt(text.encode('utf-8'))
            return base64.b64encode(encrypted_text).decode('utf-8')

        elif mode == 'decrypt':
            if text is None or private_key_str is None:
                raise ValueError("Encrypted text and private key must be provided for decryption")
            private_key = RSA.import_key(private_key_str.encode('utf-8'))
            cipher = PKCS1_OAEP.new(private_key)
            encrypted_text = base64.b64decode(text)
            decrypted_text = cipher.decrypt(encrypted_text).decode('utf-8')
            return decrypted_text

        else:
            raise ValueError("Mode must be 'encrypt', 'decrypt', or 'generate_keypair'")
