#include <gtest/gtest.h>

#include "../src/function_pyphonetics_metaphone.cpp"
// 测试类定义
TEST(PhoneticProcessorTest, BasicString)
{
    PhoneticProcessor processor;
    EXPECT_EQ(processor.generate_phonetics("discrimination"), "TSKRMNXN");
}

TEST(PhoneticProcessorTest, EmptyString)
{
    PhoneticProcessor processor;
    EXPECT_EQ(processor.generate_phonetics(""), "The given string is empty.");
}

TEST(PhoneticProcessorTest, SingleWord)
{
    PhoneticProcessor processor;
    EXPECT_EQ(processor.generate_phonetics("hello"), "HL");
}

TEST(PhoneticProcessorTest, CaseInsensitivity)
{
    PhoneticProcessor processor;
    EXPECT_EQ(processor.generate_phonetics("Hello"), "HL");
}

TEST(PhoneticProcessorTest, LongWord)
{
    PhoneticProcessor processor;
    EXPECT_EQ(processor.generate_phonetics("internationalization"), "INTRNXNLSXN");
}

int main(int argc, char **argv)
{
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}