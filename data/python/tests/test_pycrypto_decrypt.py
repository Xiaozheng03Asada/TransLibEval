import unittest
from function_pycrypto_decrypt import AESCipher

class TestAESAESCipherProcess(unittest.TestCase):

    def setUp(self):
        self.plaintext = 'This is a test message for AES.'
        self.key = 'thisis256bitkey______________'

    def test_encrypt_decrypt_valid_text(self):
        encrypted = AESCipher.process(self.key, self.plaintext, mode='encrypt')
        decrypted = AESCipher.process(self.key, encrypted, mode='decrypt')
        self.assertEqual(decrypted, self.plaintext)

    def test_decrypt_invalid_encrypted_text(self):
        invalid_encrypted_text = 'invalidtext'
        with self.assertRaises(ValueError):
            AESCipher.process(self.key, invalid_encrypted_text, mode='decrypt')

    def test_encrypt_decrypt_empty_string(self):
        encrypted_empty = AESCipher.process(self.key, '', mode='encrypt')
        decrypted_empty = AESCipher.process(self.key, encrypted_empty, mode='decrypt')
        self.assertEqual(decrypted_empty, '')

    def test_decrypt_after_double_encryption(self):
        encrypted_once = AESCipher.process(self.key, self.plaintext, mode='encrypt')
        decrypted_once = AESCipher.process(self.key, encrypted_once, mode='decrypt')
        self.assertEqual(decrypted_once, self.plaintext)

        encrypted_twice = AESCipher.process(self.key, decrypted_once, mode='encrypt')
        decrypted_twice = AESCipher.process(self.key, encrypted_twice, mode='decrypt')
        self.assertEqual(decrypted_twice, self.plaintext)

    def test_decrypt_with_altered_ciphertext(self):
        encrypted = AESCipher.process(self.key, self.plaintext, mode='encrypt')
        altered_encrypted = encrypted[:-1] + 'x'
        with self.assertRaises(ValueError):
            AESCipher.process(self.key, altered_encrypted, mode='decrypt')

if __name__ == '__main__':
    unittest.main()
