#include <gtest/gtest.h>
#include "../src/function_pyphonetics_phonetics.cpp"

class TestPhoneticProcessor : public ::testing::Test {
protected:
    void SetUp() override {
        processor = new PhoneticProcessor();
    }
    void TearDown() override {
        delete processor;
    }
    PhoneticProcessor* processor;
};

TEST_F(TestPhoneticProcessor, BasicString) {
    EXPECT_EQ(processor->generate_soundex("hello"), "H400");
}

TEST_F(TestPhoneticProcessor, SameSound) {
    EXPECT_EQ(processor->generate_soundex("halo"), "H400");
}

TEST_F(TestPhoneticProcessor, DifferentSound) {
    EXPECT_EQ(processor->generate_soundex("world"), "W643");
}

TEST_F(TestPhoneticProcessor, EmptyString) {
    EXPECT_EQ(processor->generate_soundex(""), "The given string is empty.");
}

TEST_F(TestPhoneticProcessor, SingleCharacter) {
    EXPECT_EQ(processor->generate_soundex("a"), "A000");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}