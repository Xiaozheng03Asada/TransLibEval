#include <gtest/gtest.h>
#include "../src/function_pyphonetics_distance.cpp"

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

TEST_F(TestSoundexProcessor, RefinedSoundexDistanceEqual) {
    EXPECT_EQ(processor->compute_distance("Rupert", "Robert", "refined"), "2");
}

TEST_F(TestSoundexProcessor, RefinedSoundexDistanceNotEqual) {
    EXPECT_EQ(processor->compute_distance("assign", "assist", "refined"), "2");
}

TEST_F(TestSoundexProcessor, HammingDistanceEqual) {
    EXPECT_EQ(processor->compute_distance("hello", "hxllo", "hamming"), "1");
}

TEST_F(TestSoundexProcessor, HammingDistanceNotEqual) {
    EXPECT_EQ(processor->compute_distance("katiang", "sitting", "hamming"), "1");
}

TEST_F(TestSoundexProcessor, InvalidMetric) {
    EXPECT_EQ(processor->compute_distance("hello", "hxllo", "invalid"), 
              "Invalid metric. Only 'refined' and 'hamming' are supported.");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}