#include <gtest/gtest.h>
#include "../src/function_spacy_dep.cpp"

TEST(DependencyParserTest, GetPosTagsEmptyText) {
    DependencyParser parser;
    std::string result = parser.test_dep("");
    EXPECT_EQ(result, "");
}

TEST(DependencyParserTest, SubjectVerbObjectSentence) {
    DependencyParser parser;
    std::string text = "I love flowers";
    std::string result = parser.test_dep(text);
    EXPECT_EQ(result, "I (nsubj), love (ROOT), flowers (dobj)");
}

TEST(DependencyParserTest, NewComplexSentence) {
    DependencyParser parser;
    std::string text = "The quick brown fox jumped over the lazy dog";
    std::string expected_result = "The (det), quick (amod), brown (amod), fox (nsubj), jumped (ROOT), over (prep), the (det), lazy (amod), dog (pobj)";
    EXPECT_EQ(parser.test_dep(text), expected_result);
}

TEST(DependencyParserTest, WordDifferentDependencies) {
    DependencyParser parser;
    std::string result1 = parser.test_dep("The dog barked loudly.");
    std::string result2 = parser.test_dep("I saw the dog with a collar.");
    EXPECT_NE(result1.find("dog (nsubj)"), std::string::npos);
    EXPECT_NE(result2.find("dog (dobj)"), std::string::npos);
}

TEST(DependencyParserTest, GetPosTagsNonStringInput) {
    DependencyParser parser;
    EXPECT_THROW(parser.test_dep("123"), std::invalid_argument);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}