import unittest
from function_spacy_pos import SpacyPosProcessor


class TestGetPosTags(unittest.TestCase):

    def test_get_pos_tags_empty_text(self):
        processor = SpacyPosProcessor()
        result = processor.test_pos("")
        self.assertEqual(type(result), str)  # 确保返回结果是字符串

    def test_noun_verb_adjective_adverb_punctuation(self):
        processor = SpacyPosProcessor()
        text = "The quick brown fox jumps quickly. And it's a good day!"
        result = processor.test_pos(text)

        # 验证每个单词和词性
        self.assertIn("fox (NOUN)", result)
        self.assertIn("day (NOUN)", result)
        self.assertIn("jumps (VERB)", result)
        self.assertIn("quick (ADJ)", result)
        self.assertIn("good (ADJ)", result)
        self.assertIn("quickly (ADV)", result)
        self.assertIn(". (PUNCT)", result)
        self.assertIn("! (PUNCT)", result)

    def test_get_pos_tags_sentence(self):
        processor = SpacyPosProcessor()
        text = "The quick brown fox jumps over the lazy dog."
        result = processor.test_pos(text)
        self.assertEqual(len(result.split(", ")), 10)  # 确保返回的词性标注数量为10

    def test_get_pos_tags_sentence_type(self):
        processor = SpacyPosProcessor()
        text = "The quick brown fox jumps over the lazy dog."
        result = processor.test_pos(text)
        # 确保每个词和词性都是字符串类型
        for word_pos in result.split(", "):
            word, pos = word_pos.split(" ")
            self.assertEqual(type(word), str)
            self.assertEqual(type(pos), str)

    def test_get_pos_tags_non_string_input(self):
        processor = SpacyPosProcessor()
        with self.assertRaises(TypeError):
            processor.test_pos(123)  # 输入非字符串时抛出异常


if __name__ == '__main__':
    unittest.main()
