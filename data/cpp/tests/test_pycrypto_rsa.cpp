#include <gtest/gtest.h>
#include "../src/function_pycrypto_rsa.cpp"


class TestRSAProcess : public ::testing::Test {
protected:
    void SetUp() override {
        rsa_crypto = new RSACrypto();
        std::string key_pair = rsa_crypto->rsa_process("", "generate_keypair");
        
        // 分割密钥对
        size_t separator_pos = key_pair.find("\n");
        private_key = key_pair.substr(0, separator_pos);
        public_key = key_pair.substr(separator_pos + 1);
        
        plaintext = "This is a test message for rsa_process.";
    }
    
    void TearDown() override {
        delete rsa_crypto;
    }
    
    RSACrypto* rsa_crypto;
    std::string private_key;
    std::string public_key;
    std::string plaintext;
};

TEST_F(TestRSAProcess, TestRsaEncryptDecrypt) {
    std::string encrypted = rsa_crypto->rsa_process(plaintext, "encrypt", 2048, public_key);
    std::string decrypted = rsa_crypto->rsa_process(encrypted, "decrypt", 2048, "", private_key);
    EXPECT_EQ(decrypted, plaintext);
}

TEST_F(TestRSAProcess, TestDecryptWithWrongKey) {
    std::string wrong_key_pair = rsa_crypto->rsa_process("", "generate_keypair");
    std::string wrong_private_key = wrong_key_pair.substr(0, wrong_key_pair.find("\n"));
    
    std::string encrypted = rsa_crypto->rsa_process(plaintext, "encrypt", 2048, public_key);
    
    EXPECT_THROW(rsa_crypto->rsa_process(encrypted, "decrypt", 2048, "", wrong_private_key), std::invalid_argument);
}

TEST_F(TestRSAProcess, TestEncryptLargeText) {
    std::string long_text(300, 'A');
    EXPECT_THROW(rsa_crypto->rsa_process(long_text, "encrypt", 2048, public_key), std::invalid_argument);
}

TEST_F(TestRSAProcess, TestInvalidMode) {
    EXPECT_THROW(rsa_crypto->rsa_process(plaintext, "invalid_mode", 2048, public_key), std::invalid_argument);
}

TEST_F(TestRSAProcess, TestRsaKeyPairMatch) {
    std::string encrypted = rsa_crypto->rsa_process(plaintext, "encrypt", 2048, public_key);
    std::string decrypted = rsa_crypto->rsa_process(encrypted, "decrypt", 2048, "", private_key);
    EXPECT_EQ(decrypted, plaintext);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}