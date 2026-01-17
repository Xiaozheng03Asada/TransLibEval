#include <gtest/gtest.h>
#include "../src/function_levenshtein_setratio.cpp"

class TestSetratio : public ::testing::Test {
protected:
    void SetUp() override {
        processor = new StringProcessor();
    }

    void TearDown() override {
        delete processor;
    }

    StringProcessor* processor;
};

TEST_F(TestSetratio, EqualStrings) {
    EXPECT_DOUBLE_EQ(processor->calculate_setratio("test", "test"), 1.0);
}

TEST_F(TestSetratio, EmptyStrings) {
    EXPECT_DOUBLE_EQ(processor->calculate_setratio("", ""), 1.0);
}

TEST_F(TestSetratio, CompletelyDifferentStrings) {
    EXPECT_DOUBLE_EQ(processor->calculate_setratio("abc", "xyz"), 0.0);
}

TEST_F(TestSetratio, PartialMatch) {
    EXPECT_NEAR(processor->calculate_setratio("kitten", "sitting"), 0.6153846153846154, 1e-10);
}

TEST_F(TestSetratio, SingleCharacterDifference) {
    EXPECT_DOUBLE_EQ(processor->calculate_setratio("a", "b"), 0.0);
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}