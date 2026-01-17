#include <gtest/gtest.h>
#include "../src/function_missingno_bar.cpp"

class TestMissingnoBar : public ::testing::Test {
protected:
    MissingnoBar missingno_bar;
};

TEST_F(TestMissingnoBar, TestCompleteData) {
    std::string csv_data = "A,B,C\n1,2,3\n4,5,6\n7,8,9";
    EXPECT_EQ(missingno_bar.visualize_missing_data(csv_data), "Missing data visualization generated.");
}

TEST_F(TestMissingnoBar, TestMissingValues) {
    std::string csv_data = "A,B,C\n1,2,\n,5,6\n7,,9";
    EXPECT_EQ(missingno_bar.visualize_missing_data(csv_data), "Missing data visualization generated.");
}

TEST_F(TestMissingnoBar, TestAllMissing) {
    std::string csv_data = "A,B,C\n,,\n,,\n,,";
    EXPECT_EQ(missingno_bar.visualize_missing_data(csv_data), "Missing data visualization generated.");
}

TEST_F(TestMissingnoBar, TestEmptyCSV) {
    std::string csv_data = "";
    EXPECT_THROW(missingno_bar.visualize_missing_data(csv_data), std::invalid_argument);
}

TEST_F(TestMissingnoBar, TestIncorrectColumnCount) {
    std::string csv_data = "A,B,C\n1,2\n4,5,6,7\n8,9";
    EXPECT_THROW(missingno_bar.visualize_missing_data(csv_data), std::invalid_argument);
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}