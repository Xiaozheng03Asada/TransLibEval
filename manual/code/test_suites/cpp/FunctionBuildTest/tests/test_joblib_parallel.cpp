#include <gtest/gtest.h>
#include "../src/function_joblib_parallel.cpp"
#include <random>  

class TestParallelFunction : public testing::Test {
protected:
    ParallelProcessor processor;
};

TEST_F(TestParallelFunction, BasicTest) {
    std::vector<boost::any> data = {0, 1, 2, 3, 4};
    std::string result = processor.run_parallel(data, 2);
    int count = std::count(result.begin(), result.end(), ',') + 1;
    EXPECT_EQ(count, 5);
}

TEST_F(TestParallelFunction, EmptyData) {
    std::vector<boost::any> data;
    std::string result = processor.run_parallel(data, 2);
    EXPECT_EQ(result, "");
}

TEST_F(TestParallelFunction, MixedDataTypes) {
    std::vector<boost::any> data;
    data.push_back(1);           
    data.push_back(2.5);        
    data.push_back("3+4j");     
    std::string result = processor.run_parallel(data, 2);
    size_t count = std::count(result.begin(), result.end(), ',') + 1;
    EXPECT_EQ(count, 2); 
}

TEST_F(TestParallelFunction, MaxInt) {
    std::vector<boost::any> data = {1, static_cast<int>(std::pow(2, 31) - 1)};
    std::string result = processor.run_parallel(data, 2);
    int count = std::count(result.begin(), result.end(), ',') + 1;
    EXPECT_EQ(count, 2);
}

TEST_F(TestParallelFunction, RandomData) {
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<> dis(-1000, 1000);
    std::vector<boost::any> data = {dis(gen), dis(gen)};
    std::string result = processor.run_parallel(data, -1);
    int count = std::count(result.begin(), result.end(), ',') + 1;
    EXPECT_EQ(count, 2);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}