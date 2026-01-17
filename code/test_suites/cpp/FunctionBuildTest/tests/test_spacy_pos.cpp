#include <gtest/gtest.h>
#include "../src/function_spacy_pos.cpp"

TEST(GetPosTagsTest, EmptyText) { 
    TextProcessor processor;
    auto result = processor.analyze("");
    EXPECT_TRUE(result.empty());
}

TEST(GetPosTagsTest, NounVerbAdjectiveAdverbPunctuation) {
    TextProcessor processor;
    std::string text = "The quick brown fox jumps quickly. And it's a good day!";
    auto result = processor.analyze(text);

    // 预期结果
    std::string expected = "The/NOUN quick/ADJ brown/ADJ fox/NOUN jumps/VERB quickly/ADV ./PUNCT And/NOUN it's/NOUN a/NOUN good/ADJ day/NOUN !/PUNCT";

    // 验证结果
    EXPECT_EQ(result, expected);
}

TEST(GetPosTagsTest, Sentence) {
    TextProcessor processor;
    std::string text = "The quick brown fox jumps over the lazy dog.";
    auto result = processor.analyze(text);
    std::string expected = "The/NOUN quick/ADJ brown/ADJ fox/NOUN jumps/VERB over/NOUN the/NOUN lazy/ADJ dog/NOUN ./PUNCT";
    EXPECT_EQ(result, expected);  // 确保返回的词性标注数量为10
}

TEST(GetPosTagsTest, SentenceType) {
    TextProcessor processor;
    std::string text = "The quick brown fox jumps over the lazy dog.";
    auto result = processor.analyze(text);
    
    // 确保返回值是字符串类型
    EXPECT_EQ(typeid(result).name(), typeid(std::string).name());
}

TEST(GetPosTagsTest, NonStringInput) {
    TextProcessor processor;
    EXPECT_THROW(processor.analyze(123), std::invalid_argument);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}