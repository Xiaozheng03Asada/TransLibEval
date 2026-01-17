#include <gtest/gtest.h>
#include "../src/function_request_head.cpp"

TEST(RequestHandlerTest, SuccessTest) {
    RequestHandler handler;
    EXPECT_EQ(handler.handle_request("https://www.example.com"), "success");
}

TEST(RequestHandlerTest, RedirectTest) {
    RequestHandler handler;
    EXPECT_EQ(handler.handle_request("https://httpstat.us/301"), "redirect 301");
}

TEST(RequestHandlerTest, Error403Test) {
    RequestHandler handler;
    EXPECT_EQ(handler.handle_request("https://httpstat.us/403"), "error 403");
}

TEST(RequestHandlerTest, Error500Test) {
    RequestHandler handler;
    EXPECT_EQ(handler.handle_request("https://httpstat.us/500"), "error 500");
}

TEST(RequestHandlerTest, TimeoutTest) {
    RequestHandler handler;
    EXPECT_EQ(handler.handle_request("https://10.255.255.1"), "timeout");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}