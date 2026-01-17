#include <gtest/gtest.h>
#include "../src/function_request_delete.cpp"

class TestHandleDeleteRequest : public ::testing::Test {
protected:
    RequestUtils utils;
};

TEST_F(TestHandleDeleteRequest, test_success_delete) {
    std::string result = utils.handle_delete_request("https://jsonplaceholder.typicode.com/posts/1");
    ASSERT_EQ(result, "success");
}

TEST_F(TestHandleDeleteRequest, test_error_delete) {
    std::string result = utils.handle_delete_request("https://api.github.com/repos/nonexistentrepo");
    ASSERT_EQ(result, "error 404");
}

TEST_F(TestHandleDeleteRequest, test_timeout_delete) {
    std::string result = utils.handle_delete_request("https://10.255.255.1");
    ASSERT_EQ(result, "timeout");
}

TEST_F(TestHandleDeleteRequest, test_delete_nonexistent_user) {
    std::string result = utils.handle_delete_request("https://api.github.com/users/nonexistentuser");
    ASSERT_EQ(result, "error 404");
}

TEST_F(TestHandleDeleteRequest, test_delete_invalid_url) {
    std::string result = utils.handle_delete_request("https://expired.badssl.com/");
    ASSERT_EQ(result, "ssl_error");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}