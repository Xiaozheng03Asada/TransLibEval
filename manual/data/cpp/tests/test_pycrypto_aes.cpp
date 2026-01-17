#include <gtest/gtest.h>
#include "../src/function_pycrypto_aes.cpp"


class TestAESCipher : public ::testing::Test {
protected:
    void SetUp() override {
        key = "mysecretpassword";
        plaintext = "This is a test message for AESCipher.";
        cipher = new AESCipher();
    }

    void TearDown() override {
        delete cipher;
    }

    std::string key;
    std::string plaintext;
    AESCipher* cipher;
};

TEST_F(TestAESCipher, EncryptDecryptBasicText) {
    std::string plaintext = "Hello, World!@#$%^&*()_+-=~1234567890";
    std::string encrypted = cipher->process(plaintext, key, "encrypt");
    std::string decrypted = cipher->process(encrypted, key, "decrypt");
    EXPECT_EQ(decrypted, plaintext);
}

TEST_F(TestAESCipher, DecryptWithWrongKey) {
    std::string plaintext = "This is a test message.";
    std::string encrypted = cipher->process(plaintext, key, "encrypt");
    std::string wrong_key = "wrongpassword";
    EXPECT_THROW(cipher->process(encrypted, wrong_key, "decrypt"), std::runtime_error);
}

TEST_F(TestAESCipher, DecryptPartialCiphertext) {
    std::string plaintext = "Partial decryption test";
    std::string encrypted = cipher->process(plaintext, key, "encrypt");
    std::string partial_encrypted = encrypted.substr(0, encrypted.length() / 2);
    EXPECT_THROW(cipher->process(partial_encrypted, key, "decrypt"), std::runtime_error);
}

TEST_F(TestAESCipher, DecryptTamperedCiphertext) {
    std::string plaintext = "Tamper test";
    std::string encrypted = cipher->process(plaintext, key, "encrypt");
    std::string tampered_encrypted = encrypted.substr(0, encrypted.length()-1) + "X";
    EXPECT_THROW(cipher->process(tampered_encrypted, key, "decrypt"), std::runtime_error);
}

TEST_F(TestAESCipher, AES128_192_256BitKey) {
    std::string plaintext = this->plaintext;

    std::string key_128 = "thisis128bitkey";
    std::string encrypted = cipher->process(plaintext, key_128, "encrypt");
    std::string decrypted = cipher->process(encrypted, key_128, "decrypt");
    EXPECT_EQ(decrypted, plaintext);

    std::string key_192 = "thisis192bitkey______";
    encrypted = cipher->process(plaintext, key_192, "encrypt");
    decrypted = cipher->process(encrypted, key_192, "decrypt");
    EXPECT_EQ(decrypted, plaintext);

    std::string key_256 = "thisis256bitkey______________";
    encrypted = cipher->process(plaintext, key_256, "encrypt");
    decrypted = cipher->process(encrypted, key_256, "decrypt");
    EXPECT_EQ(decrypted, plaintext);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}