#include <gtest/gtest.h>
#include "../src/function_gensim_models_Word2Vec.cpp"

class TestWord2VecModelTrainer : public ::testing::Test {
protected:
    Word2VecModelTrainer trainer;
};

TEST_F(TestWord2VecModelTrainer, TestTrainWord2VecModel) {
    std::string result = trainer.train_word2vec_model("dog barks;cat meows;dog chases cat");
    EXPECT_TRUE(result.find("dog") != std::string::npos);
    EXPECT_TRUE(result.find("cat") != std::string::npos);
}

TEST_F(TestWord2VecModelTrainer, TestSimilarityWordsExist) {
    std::string result = trainer.train_word2vec_model("apple orange;banana grape");
    EXPECT_TRUE(result.find("apple") != std::string::npos);
    EXPECT_TRUE(result.find("orange") != std::string::npos);
}

TEST_F(TestWord2VecModelTrainer, TestEmptyInput) {
    std::string result = trainer.train_word2vec_model("");
    EXPECT_EQ(result, "Training failed");
}

TEST_F(TestWord2VecModelTrainer, TestSingleWordSentences) {
    std::string result = trainer.train_word2vec_model("hello;world;test");
    EXPECT_TRUE(result.find("hello") != std::string::npos);
    EXPECT_TRUE(result.find("world") != std::string::npos);
    EXPECT_TRUE(result.find("test") != std::string::npos);
}

TEST_F(TestWord2VecModelTrainer, TestLargeSentence) {
    std::ostringstream sentence;
    for (int i = 0; i < 100; ++i) {
        sentence << "word" << i << " ";
    }
    std::string result = trainer.train_word2vec_model(sentence.str());
    EXPECT_TRUE(result.find("word0") != std::string::npos);
    EXPECT_TRUE(result.find("word99") != std::string::npos);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}