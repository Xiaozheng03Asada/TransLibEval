import unittest
from function_pycrypto_sha256_hasher import SHA256Hasher

class TestSHA256Hasher(unittest.TestCase):

    def setUp(self):
        self.hasher = SHA256Hasher()

    def test_special_characters_hash(self):
        special_text = "This is a test! "
        result = self.hasher.hash_text(special_text)
        expected_hash = self.hasher.hash_text(special_text) 
        self.assertEqual(result, expected_hash)

    def test_hash_is_unique_for_different_inputs(self):
        text1 = "Test input one"
        text2 = "Test input two"
        hash1 = self.hasher.hash_text(text1)
        hash2 = self.hasher.hash_text(text2)
        self.assertNotEqual(hash1, hash2)

    def test_empty_string_hash(self):
        result = self.hasher.hash_text("")
        expected_hash = self.hasher.hash_text("") 
        self.assertEqual(result, expected_hash)

    def test_collision_resistance(self):
        input1 = "Hello, world!"
        input2 = "Hello, world!!"  
        hash1 = self.hasher.hash_text(input1)
        hash2 = self.hasher.hash_text(input2)
        self.assertNotEqual(hash1, hash2)

    def test_length_invariance(self):
        small_input = "Short text"
        large_input = "A" * 1000  
        hash_small = self.hasher.hash_text(small_input)
        hash_large = self.hasher.hash_text(large_input)
        self.assertEqual(len(hash_small), 64)  
        self.assertEqual(len(hash_large), 64)

if __name__ == '__main__':
    unittest.main()
