import unittest
from function_spacy_vector import SpacyVectorProcessor
from unittest.mock import patch


class TestGetVectorLengths(unittest.TestCase):

    def test_function_called(self):
        text = "hello world"
        with patch('function_spacy_vector.spacy.load') as mock_load:
            processor = SpacyVectorProcessor()
            processor.vector_lengths(text)
            mock_load.assert_called_once_with('en_core_web_sm')

    def test_get_vector_lengths_length_matches_tokens(self):
        processor = SpacyVectorProcessor()
        text = "hello world"
        hello_vector_length = 8.736436  # 请根据实际运行结果更新
        world_vector_length = 7.311293  # 请根据实际运行结果更新
        expected_lengths = f"hello: {hello_vector_length:.6f}, world: {world_vector_length:.6f}"
        result_lengths = processor.vector_lengths(text)
        self.assertEqual(expected_lengths, result_lengths)

    def test_vector_lengths_with_punctuation(self):
        processor = SpacyVectorProcessor()
        text = "hello, world!"
        lengths_str = processor.vector_lengths(text)
        self.assertEqual(len(lengths_str.split(", ")), 4)  # 确保正确计算标点符号的词向量

    def test_vector_lengths_case_sensitivity(self):
        processor = SpacyVectorProcessor()
        text1 = "Hello"
        text2 = "hello"
        lengths1_str = processor.vector_lengths(text1)
        lengths2_str = processor.vector_lengths(text2)

        # 解析出词向量长度并转换为浮点数
        lengths1 = [float(length.split(":")[1].strip()) for length in lengths1_str.split(", ")]
        lengths2 = [float(length.split(":")[1].strip()) for length in lengths2_str.split(", ")]

        self.assertEqual(len(lengths1), len(lengths2))
        self.assertNotEqual(lengths1[0], lengths2[0])  # 确保它们的值不同

    def test_output_type(self):
        processor = SpacyVectorProcessor()
        text = "hello world"
        result = processor.vector_lengths(text)
        self.assertEqual(type(result), str)  # 确保返回的是字符串


if __name__ == '__main__':
    unittest.main()
