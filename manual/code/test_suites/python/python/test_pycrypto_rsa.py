import unittest
from function_pycrypto_rsa import RSACrypto

class TestRSAProcess(unittest.TestCase):

    def setUp(self):
        self.rsa_crypto = RSACrypto()
        self.private_key, self.public_key = self.rsa_crypto.rsa_process(mode='generate_keypair')
        self.plaintext = 'This is a test message for rsa_process.'

    def test_rsa_encrypt_decrypt(self):
        encrypted = self.rsa_crypto.rsa_process(self.plaintext, mode='encrypt', public_key_str=self.public_key)
        decrypted = self.rsa_crypto.rsa_process(encrypted, mode='decrypt', private_key_str=self.private_key)
        self.assertEqual(decrypted, self.plaintext)

    def test_decrypt_with_wrong_key(self):
        wrong_private_key, _ = self.rsa_crypto.rsa_process(mode='generate_keypair')
        encrypted = self.rsa_crypto.rsa_process(self.plaintext, mode='encrypt', public_key_str=self.public_key)
        with self.assertRaises(ValueError):
            self.rsa_crypto.rsa_process(encrypted, mode='decrypt', private_key_str=wrong_private_key)

    def test_encrypt_large_text(self):
        long_text = 'A' * 300
        with self.assertRaises(ValueError):
            self.rsa_crypto.rsa_process(long_text, mode='encrypt', public_key_str=self.public_key)

    def test_invalid_mode(self):
        with self.assertRaises(ValueError):
            self.rsa_crypto.rsa_process(self.plaintext, mode='invalid_mode', public_key_str=self.public_key)

    def test_rsa_key_pair_match(self):
        encrypted = self.rsa_crypto.rsa_process(self.plaintext, mode='encrypt', public_key_str=self.public_key)
        decrypted = self.rsa_crypto.rsa_process(encrypted, mode='decrypt', private_key_str=self.private_key)
        self.assertEqual(decrypted, self.plaintext)

if __name__ == '__main__':
    unittest.main()
