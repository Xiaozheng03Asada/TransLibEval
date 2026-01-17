from function_boltons_iterutils_chunked import ChunkManager
import unittest


class TestChunkManager(unittest.TestCase):
    def setUp(self):
        self.function = ChunkManager()

    def test_chunk_data_valid_input(self):
        data_str = "1,2,3,4,5,6,7,8"
        chunk_size = 3
        result = self.function.chunk_data(data_str, chunk_size)
        expected = "1,2,3;4,5,6;7,8"
        self.assertEqual(result, expected)

    def test_chunk_data_empty_string(self):
        data_str = "1,2,3,4,5,6,7,8"
        chunk_size = 3
        result = self.function.chunk_data(data_str, chunk_size)
        expected = "1,2,3;4,5,6;7,8"
        self.assertEqual(result, expected)

    def test_chunk_data_single_element(self):
        data_str = "1"
        chunk_size = 3
        result = self.function.chunk_data(data_str, chunk_size)
        expected = "1"
        self.assertEqual(result, expected)

    def test_chunk_data_large_chunk_size(self):
        data_str = "1,2,3,4,5,6,7,8"
        chunk_size = 10
        result = self.function.chunk_data(data_str, chunk_size)
        expected = "1,2,3,4,5,6,7,8"
        self.assertEqual(result, expected)

    def test_chunk_data_invalid_input(self):
        data_str = "1,2,3,4,5,6"
        chunk_size = "three"  # Invalid chunk_size
        result = self.function.chunk_data(data_str, chunk_size)
        expected = "Error"
        self.assertEqual(result, expected)


if __name__ == "__main__":
    unittest.main()
