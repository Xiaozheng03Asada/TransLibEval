#include <gtest/gtest.h>
#include "../src/function_request_post.cpp"

class TestHandlePostRequest : public ::testing::Test {
protected:
    PostRequestHandler handler;
};

TEST_F(TestHandlePostRequest, test_success_post) {
    std::string result = handler.handle_post_request(
        "https://jsonplaceholder.typicode.com/posts", "foo", "bar", 1);
    ASSERT_EQ(result, "success");
}

TEST_F(TestHandlePostRequest, test_error_post) {
    std::string result = handler.handle_post_request(
        "https://jsonplaceholder.typicode.com/invalid", "foo", "bar", 1);
    ASSERT_EQ(result, "error 404");
}

TEST_F(TestHandlePostRequest, test_timeout_post) {
    std::string result = handler.handle_post_request(
        "https://10.255.255.1", "foo", "bar", 1);
    ASSERT_EQ(result, "timeout");
}

TEST_F(TestHandlePostRequest, test_large_data_post) {
    std::string large_data_title(30000, 'f');
    std::string large_data_body(30000, 'b');
    std::string result = handler.handle_post_request(
        "https://jsonplaceholder.typicode.com/posts", 
        large_data_title, large_data_body, 1);
    ASSERT_EQ(result, "success");
}

TEST_F(TestHandlePostRequest, test_post_with_empty_body) {
    std::string result = handler.handle_post_request(
        "https://jsonplaceholder.typicode.com/posts", "", "", 1);
    ASSERT_EQ(result, "success");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}