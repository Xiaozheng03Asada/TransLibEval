#include <gtest/gtest.h>
#include "../src/function_boltons_iterutils_chunked.cpp"
class TestChunkManager : public ::testing::Test {
protected:
    ChunkManager manager;
};

TEST_F(TestChunkManager, ValidInput) {
    std::string data_str = "1,2,3,4,5,6,7,8";
    int chunk_size = 3;
    std::string result = manager.chunk_data(data_str, chunk_size);
    EXPECT_EQ(result, "1,2,3;4,5,6;7,8");
}

TEST_F(TestChunkManager, EmptyString) {
    std::string data_str = "";
    int chunk_size = 3;
    std::string result = manager.chunk_data(data_str, chunk_size);
    EXPECT_EQ(result, "");
}

TEST_F(TestChunkManager, SingleElement) {
    std::string data_str = "1";
    int chunk_size = 3;
    std::string result = manager.chunk_data(data_str, chunk_size);
    EXPECT_EQ(result, "1");
}

TEST_F(TestChunkManager, LargeChunkSize) {
    std::string data_str = "1,2,3,4,5,6,7,8";
    int chunk_size = 10;
    std::string result = manager.chunk_data(data_str, chunk_size);
    EXPECT_EQ(result, "1,2,3,4,5,6,7,8");
}

TEST_F(TestChunkManager, InvalidChunkSize) {
    std::string data_str = "1,2,3,4,5,6";
    int chunk_size = -1;
    std::string result = manager.chunk_data(data_str, chunk_size);
    EXPECT_EQ(result, "Error");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}