#include <gtest/gtest.h>
#include "../src/function_missingno_dendrogram.cpp"

class TestMissingnoDendrogram : public ::testing::Test {
protected:
    MissingnoDendrogram missingno_dendrogram;
};

TEST_F(TestMissingnoDendrogram, TestFailedEmptyData) {
    std::string data = "";
    EXPECT_EQ(missingno_dendrogram.create_dendrogram(data), "failed");
}

TEST_F(TestMissingnoDendrogram, TestFailedInvalidData) {
    std::string data = "A,B,C\n1,2,3\n4,5,6\ninvalid_data";
    EXPECT_EQ(missingno_dendrogram.create_dendrogram(data), "failed");
}

TEST_F(TestMissingnoDendrogram, TestFailedInvalidFormat) {
    std::string data = "A,B,C\n1;2;3\n4;5;6\n7;8;9";
    EXPECT_EQ(missingno_dendrogram.create_dendrogram(data), "failed");
}

TEST_F(TestMissingnoDendrogram, TestNonDataframeInput) {
    std::string data = "text without proper format";
    EXPECT_EQ(missingno_dendrogram.create_dendrogram(data), "failed");
}

TEST_F(TestMissingnoDendrogram, TestFailedIncompleteData) {
    std::string data = "A,B,C\n1,2\n4,5\n7";
    EXPECT_EQ(missingno_dendrogram.create_dendrogram(data), "failed");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}