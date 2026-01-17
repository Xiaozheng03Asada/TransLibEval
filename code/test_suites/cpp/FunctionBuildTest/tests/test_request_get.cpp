#include <gtest/gtest.h>
#include "../src/function_request_get.cpp"

class TestHandleRequest : public ::testing::Test {
protected:
    RequestHandler handler;
};

TEST_F(TestHandleRequest, test_success) {
    std::string result = handler.handle_request("https://jsonplaceholder.typicode.com/posts/1");
    EXPECT_EQ(result, "success");
}

TEST_F(TestHandleRequest, test_redirect) {
    std::string result = handler.handle_request("https://httpstat.us/301");
    EXPECT_EQ(result, "redirect 301");
}

TEST_F(TestHandleRequest, test_error_403) {
    std::string result = handler.handle_request("https://httpstat.us/403");
    EXPECT_EQ(result, "error 403");
}

TEST_F(TestHandleRequest, test_error_500) {
    std::string result = handler.handle_request("https://httpstat.us/500");
    EXPECT_EQ(result, "error 500");
}

TEST_F(TestHandleRequest, test_timeout) {
    std::string result = handler.handle_request("https://10.255.255.1");
    EXPECT_EQ(result, "timeout");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    int result = RUN_ALL_TESTS();
    return result;
}