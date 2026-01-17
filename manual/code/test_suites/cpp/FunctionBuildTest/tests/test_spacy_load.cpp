#include <gtest/gtest.h>
#include "../src/function_spacy_load.cpp"

TEST(SpacyTextTest, NormalText) {
    std::string text = "This is a normal sentence.";
    std::string expected = "This is a normal sentence .";
    auto result = SpacyTextProcessor::spacy_text(text);
    EXPECT_EQ(result, expected);
}

TEST(SpacyTextTest, TextWithDuplicateWords) {
    std::string text = "apple apple banana apple";
    std::string expected = "apple apple banana apple";
    auto result = SpacyTextProcessor::spacy_text(text);
    EXPECT_EQ(result, expected);
}

TEST(SpacyTextTest, MemoryError) {
    try {
        // 创建更大的输入以触发内存错误
        const size_t size = 1024 * 1024 * 1024 + 1; // 超过 1GB
        std::string text(size, 'a');
        
        auto result = SpacyTextProcessor::spacy_text(text);
        FAIL() << "Expected std::runtime_error";
    }
    catch(const std::runtime_error& e) {
        EXPECT_STREQ(e.what(), "Insufficient memory error");
    }
    catch(...) {
        FAIL() << "Wrong exception type thrown";
    }
}

TEST(SpacyTextTest, OtherError) {
    try {
        SpacyTextProcessor::spacy_text(std::string());  // 触发其他错误
        FAIL() << "Expected std::runtime_error";
    }
    catch(const std::runtime_error& e) {
        std::string error_msg(e.what());
        EXPECT_EQ(error_msg.substr(0, 13), "Other errors:");
    }
}

TEST(SpacyTextTest, MultilingualText) {
    std::string text = "Bonjour, this is a test. 你好";
    std::string expected = "Bonjour , this is a test . 你好";
    auto result = SpacyTextProcessor::spacy_text(text);
    EXPECT_EQ(result, expected);
}


int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}