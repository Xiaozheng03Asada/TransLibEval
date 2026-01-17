#include <gtest/gtest.h>
#include "../src/function_Pydantic_constr.cpp"

class TestCreateUserProfile : public ::testing::Test {
protected:
    void SetUp() override {
        handler = new UserProfileHandler();
    }

    void TearDown() override {
        delete handler;
    }
    
    UserProfileHandler* handler;
};

TEST_F(TestCreateUserProfile, ValidProfile) {
    std::string result = handler->create_user_profile("user_123", "user@example.com");
    EXPECT_EQ(result, "username='user_123' email='user@example.com'");
}

TEST_F(TestCreateUserProfile, InvalidEmail) {
    std::string result = handler->create_user_profile("user123", "user.com");
    EXPECT_TRUE(result.find("value is not a valid email address") != std::string::npos);
}

TEST_F(TestCreateUserProfile, ValidProfileWithMinLengthUsername) {
    std::string result = handler->create_user_profile("abc", "test@example.com");
    EXPECT_EQ(result, "username='abc' email='test@example.com'");
}

TEST_F(TestCreateUserProfile, ValidProfileWithMaxLengthUsername) {
    std::string result = handler->create_user_profile(std::string(20, 'a'), "longusername@example.com");
    EXPECT_EQ(result, "username='aaaaaaaaaaaaaaaaaaaa' email='longusername@example.com'");
}

TEST_F(TestCreateUserProfile, InvalidEmailMissingDomain) {
    std::string result = handler->create_user_profile("user123", "user@");
    EXPECT_TRUE(result.find("value is not a valid email address") != std::string::npos);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}