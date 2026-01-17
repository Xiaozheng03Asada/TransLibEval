import unittest
from function_pycrypto_aes import AESCipher

class TestAESCipher(unittest.TestCase):

    def setUp(self):
        self.key = 'mysecretpassword'
        self.plaintext = 'This is a test message for AESCipher.'
        self.cipher = AESCipher()

    def test_encrypt_decrypt_basic_text(self):
        plaintext = 'Hello, World!@#$%^&*()_+-=~1234567890'
        encrypted = self.cipher.process(plaintext, self.key, mode='encrypt')
        decrypted = self.cipher.process(encrypted, self.key, mode='decrypt')
        self.assertEqual(decrypted, plaintext)

    def test_decrypt_with_wrong_key(self):
        plaintext = 'This is a test message.'
        encrypted = self.cipher.process(plaintext, self.key, mode='encrypt')
        wrong_key = 'wrongpassword'
        with self.assertRaises(ValueError):
            self.cipher.process(encrypted, wrong_key, mode='decrypt')

    def test_decrypt_partial_ciphertext(self):
        plaintext = 'Partial decryption test'
        encrypted = self.cipher.process(plaintext, self.key, mode='encrypt')
        partial_encrypted = encrypted[:len(encrypted) // 2] 
        with self.assertRaises(ValueError):
            self.cipher.process(partial_encrypted, self.key, mode='decrypt')

    def test_decrypt_tampered_ciphertext(self):
        plaintext = 'Tamper test'
        encrypted = self.cipher.process(plaintext, self.key, mode='encrypt')
        tampered_encrypted = encrypted[:-1] + 'X' 
        with self.assertRaises(ValueError):
            self.cipher.process(tampered_encrypted, self.key, mode='decrypt')

    def test_aes_128_192_256_bit_key(self):
        plaintext = self.plaintext

        key_128 = 'thisis128bitkey'
        encrypted = self.cipher.process(plaintext, key_128, mode='encrypt')
        decrypted = self.cipher.process(encrypted, key_128, mode='decrypt')
        self.assertEqual(decrypted, plaintext)

        key_192 = 'thisis192bitkey______'  
        encrypted = self.cipher.process(plaintext, key_192, mode='encrypt')
        decrypted = self.cipher.process(encrypted, key_192, mode='decrypt')
        self.assertEqual(decrypted, plaintext)

        key_256 = 'thisis256bitkey______________'
        encrypted = self.cipher.process(plaintext, key_256, mode='encrypt')
        decrypted = self.cipher.process(encrypted, key_256, mode='decrypt')
        self.assertEqual(decrypted, plaintext)

if __name__ == '__main__':
    unittest.main()
