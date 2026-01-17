#include <gtest/gtest.h>
#include "../src/function_pyphonetics_sounds_like.cpp"

class TestSoundexProcessor : public ::testing::Test {
protected:
    void SetUp() override {
        processor = new SoundexProcessor();
    }

    void TearDown() override {
        delete processor;
    }

    SoundexProcessor* processor;
};

TEST_F(TestSoundexProcessor, IdenticalStrings) {
    EXPECT_EQ(processor->compare_strings("hello", "hello"), "true");
}

TEST_F(TestSoundexProcessor, SimilarSoundingWords) {
    EXPECT_EQ(processor->compare_strings("hello", "halo"), "true");
}

TEST_F(TestSoundexProcessor, DifferentSoundingWords) {
    EXPECT_EQ(processor->compare_strings("hello", "world"), "false");
}

TEST_F(TestSoundexProcessor, EmptyStrings) {
    EXPECT_EQ(processor->compare_strings("", ""), "The given string is empty.");
}

TEST_F(TestSoundexProcessor, EmptyAndNonEmpty) {
    EXPECT_EQ(processor->compare_strings("hello", ""), "The given string is empty.");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}