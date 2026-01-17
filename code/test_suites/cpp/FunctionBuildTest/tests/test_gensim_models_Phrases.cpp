#include <gtest/gtest.h>
#include "../src/function_gensim_models_Phrases.cpp"

class TestPhraseModelTrainer : public ::testing::Test {
protected:
    PhraseModelTrainer trainer;
};

TEST_F(TestPhraseModelTrainer, PhraseDetection) {
    std::string result = trainer.train_phrase_model("new york is great;new york city is big;san francisco is beautiful;los angeles is sunny", 1, 5.0);
    EXPECT_NE(result.find("new_york"), std::string::npos);
}

TEST_F(TestPhraseModelTrainer, NoShortPhrases) {
    std::string result = trainer.train_phrase_model("new york is great;new york city is big;san francisco is beautiful;los angeles is sunny", 2, 20.0);
    EXPECT_EQ(result.find("los_angeles"), std::string::npos);
}

TEST_F(TestPhraseModelTrainer, HighThreshold) {
    std::string result = trainer.train_phrase_model("new york is great;new york city is big;san francisco is beautiful;los angeles is sunny", 1, 50.0);
    EXPECT_EQ(result.find("new_york"), std::string::npos);
}

TEST_F(TestPhraseModelTrainer, SingleSentence) {
    std::string result = trainer.train_phrase_model("this is a test case", 1, 1.0);
    EXPECT_EQ(result, "No phrases detected");
}

TEST_F(TestPhraseModelTrainer, OverlappingPhrases) {
    std::string result = trainer.train_phrase_model("new york city is new york;new york is big", 1, 1.0);
    EXPECT_NE(result.find("new_york"), std::string::npos);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}