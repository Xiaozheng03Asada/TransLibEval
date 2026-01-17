#include <gtest/gtest.h>
#include "../src/function_gensim_models_KeyedVectors_most_similar.cpp"

class TestFindMostSimilarWords : public ::testing::Test {
protected:
    FindMostSimilarWords find_similar;
};

TEST_F(TestFindMostSimilarWords, TestValidWords) {
    std::string result = find_similar.find_most_similar_words("king", 3);
    EXPECT_NE(result.find("queen: 1.00"), std::string::npos);
    EXPECT_NE(result.find("man: 1.00"), std::string::npos);
}

TEST_F(TestFindMostSimilarWords, TestInvalidWord) {
    std::string result = find_similar.find_most_similar_words("nonexistent", 3);
    EXPECT_EQ(result, "Error");
}

TEST_F(TestFindMostSimilarWords, TestTopN) {
    std::string result = find_similar.find_most_similar_words("king", 2);
    int count = 0;
    size_t pos = 0;
    std::string tmp = result;
    while ((pos = tmp.find(", ", pos)) != std::string::npos) {
        count++;
        pos += 2;
    }
    EXPECT_EQ(count + 1, 2);
}

TEST_F(TestFindMostSimilarWords, TestEdgeCase) {
    std::string result = find_similar.find_most_similar_words("not_in_vocab", 3);
    EXPECT_EQ(result, "Error");
}

TEST_F(TestFindMostSimilarWords, TestEmptyModel) {
    FindMostSimilarWords empty_model;
    empty_model.is_empty_model = true; // 标记为空模型
    std::string result = empty_model.find_most_similar_words("king", 3);
    EXPECT_EQ(result, "queen: 1.00, man: 1.00, woman: 1.00");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}