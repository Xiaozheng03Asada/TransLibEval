#include <gtest/gtest.h>
#include "../src/function_gensim_train.cpp"

class TestWord2VecModelTrainer : public ::testing::Test {
protected:
    Word2VecModelTrainer trainer;
};

TEST_F(TestWord2VecModelTrainer, TestTrainWord2VecModelValid) {
    std::string result = trainer.train_word2vec_model("dog barks at cat;cat meows at dog", 50, 2, 1);
    EXPECT_NE(result.find("dog"), std::string::npos);
    EXPECT_NE(result.find("cat"), std::string::npos);
}

TEST_F(TestWord2VecModelTrainer, TestTrainWord2VecModelInvalidData) {
    std::string result = trainer.train_word2vec_model("", 50, 2, 1);
    EXPECT_EQ(result, "Training failed");
}

TEST_F(TestWord2VecModelTrainer, TestTrainWord2VecModelMinCount) {
    std::string result = trainer.train_word2vec_model("dog barks;cat meows;dog and cat", 50, 2, 2);
    EXPECT_EQ(result.find("barks"), std::string::npos);
    EXPECT_EQ(result.find("meows"), std::string::npos);
}

TEST_F(TestWord2VecModelTrainer, TestTrainWord2VecModelCustomParams) {
    std::string result = trainer.train_word2vec_model("dog barks at cat;cat meows at dog", 100, 3, 1);
    EXPECT_NE(result.find("dog"), std::string::npos);
    EXPECT_NE(result.find("cat"), std::string::npos);
}

TEST_F(TestWord2VecModelTrainer, TestTrainWord2VecModelWithSpecialCharacters) {
    std::string result = trainer.train_word2vec_model("dog barks;cat@home meows;hello! world", 50, 2, 1);
    EXPECT_NE(result.find("cat@home"), std::string::npos);
    EXPECT_NE(result.find("hello!"), std::string::npos);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}