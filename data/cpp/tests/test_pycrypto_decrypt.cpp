#include <gtest/gtest.h>
#include "../src/function_pycrypto_decrypt.cpp"

class TestAESCipherProcess : public ::testing::Test {
protected:
    void SetUp() override {
        plaintext = "This is a test message for AES.";
        key = "thisis256bitkey______________";
    }

    std::string plaintext;
    std::string key;
};

TEST_F(TestAESCipherProcess, EncryptDecryptValidText) {
    std::string encrypted = AESCipher::process(key, plaintext, "encrypt");
    std::string decrypted = AESCipher::process(key, encrypted, "decrypt");
    EXPECT_EQ(decrypted, plaintext);
}

TEST_F(TestAESCipherProcess, DecryptInvalidEncryptedText) {
    std::string invalid_encrypted_text = "invalidtext";
    EXPECT_THROW(AESCipher::process(key, invalid_encrypted_text, "decrypt"), std::invalid_argument);
}

TEST_F(TestAESCipherProcess, EncryptDecryptEmptyString) {
    std::string encrypted_empty = AESCipher::process(key, "", "encrypt");
    std::string decrypted_empty = AESCipher::process(key, encrypted_empty, "decrypt");
    EXPECT_EQ(decrypted_empty, "");
}

TEST_F(TestAESCipherProcess, DecryptAfterDoubleEncryption) {
    std::string encrypted_once = AESCipher::process(key, plaintext, "encrypt");
    std::string decrypted_once = AESCipher::process(key, encrypted_once, "decrypt");
    EXPECT_EQ(decrypted_once, plaintext);

    std::string encrypted_twice = AESCipher::process(key, decrypted_once, "encrypt");
    std::string decrypted_twice = AESCipher::process(key, encrypted_twice, "decrypt");
    EXPECT_EQ(decrypted_twice, plaintext);
}

TEST_F(TestAESCipherProcess, DecryptWithAlteredCiphertext) {
    std::string encrypted = AESCipher::process(key, plaintext, "encrypt");
    std::string altered_encrypted = encrypted.substr(0, encrypted.length() - 1) + "x";
    EXPECT_THROW(AESCipher::process(key, altered_encrypted, "decrypt"), std::invalid_argument);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}