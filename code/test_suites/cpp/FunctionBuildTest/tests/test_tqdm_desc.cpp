#include <gtest/gtest.h>
#include "../src/function_tqdm_desc.cpp"

class TestDescExample : public ::testing::Test {
protected:
    void SetUp() override {}
    void TearDown() override {}
};

TEST_F(TestDescExample, TestDescDefaultValueWhenCallingFunction) {
    std::string data = "123";
    try {
        std::string result = ProgressBar::process_with_progress_bar(data);
        EXPECT_EQ(typeid(result).name(), typeid(std::string).name());
    } catch (const std::exception& e) {
        FAIL() << "tqdm without desc parameter in function raised an unexpected exception: " << e.what();
    }
}

TEST_F(TestDescExample, TestDescTypeCheckWhenCallingFunction) {
    std::string data = "123";
    EXPECT_THROW(ProgressBar::process_with_progress_bar(data, std::string(1, '\0')), std::invalid_argument);
}

TEST_F(TestDescExample, TestDescImmutabilityWhenCallingFunction) {
    std::string data = "123";
    try {
        std::string result = ProgressBar::process_with_progress_bar(data, "Initial");
        EXPECT_EQ(result, "246");
    } catch (const std::exception& e) {
        FAIL() << "Unexpected exception when testing desc immutability: " << e.what();
    }
}

TEST_F(TestDescExample, TestDescProcessedOutputWhenCallingFunction) {
    std::string data = "123";
    std::string result = ProgressBar::process_with_progress_bar(data, "Same");
    std::string expected_result = "246";
    EXPECT_EQ(result, expected_result);
}

TEST_F(TestDescExample, TestDescLengthLimitInMemoryWhenCallingFunction) {
    std::string long_desc(10000, 'a');
    std::string data = "1";
    try {
        for (int i = 0; i < 5; ++i) {
            std::string result = ProgressBar::process_with_progress_bar(data, long_desc);
            EXPECT_EQ(typeid(result).name(), typeid(std::string).name());
        }
    } catch (const std::exception& e) {
        FAIL() << "Creating multiple tqdm instances with long desc in function raised an exception: " << e.what();
    }
}