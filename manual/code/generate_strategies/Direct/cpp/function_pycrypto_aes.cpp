#include <string>
#include <cryptopp/aes.h>
#include <cryptopp/modes.h>
#include <cryptopp/filters.h>
#include <cryptopp/base64.h>
#include <cryptopp/osrng.h>
#include <cryptopp/hex.h>
#include <stdexcept>

class AESCipher {
public:
    std::string process(const std::string& text, const std::string& key, const std::string& mode = "encrypt") {
        // 准备密钥 - 填充或截断至32字节
        std::string paddedKey = key;
        paddedKey.resize(32, ' ');
        paddedKey = paddedKey.substr(0, 32);
        
        const int blockSize = CryptoPP::AES::BLOCKSIZE;
        
        // 定义填充函数
        auto pad = [blockSize](const std::string& text) {
            int paddingLength = blockSize - (text.length() % blockSize);
            std::string result = text;
            for (int i = 0; i < paddingLength; i++) {
                result.push_back(static_cast<char>(paddingLength));
            }
            return result;
        };
        
        // 定义去除填充函数
        auto unpad = [](const std::string& text) {
            if (text.empty()) {
                throw std::runtime_error("Empty text to unpad");
            }
            unsigned char paddingLength = static_cast<unsigned char>(text[text.length() - 1]);
            if (paddingLength > text.length()) {
                throw std::runtime_error("Invalid padding");
            }
            return text.substr(0, text.length() - paddingLength);
        };
        
        if (mode == "encrypt") {
            // 生成随机IV
            CryptoPP::AutoSeededRandomPool rng;
            CryptoPP::byte iv[blockSize]; // 修改: 使用 CryptoPP::byte 而不是 byte
            rng.GenerateBlock(iv, blockSize);
            
            // 设置密钥和IV
            CryptoPP::CBC_Mode<CryptoPP::AES>::Encryption encryptor;
            encryptor.SetKeyWithIV((CryptoPP::byte*)paddedKey.data(), paddedKey.size(), iv);
            
            // 加密
            std::string paddedText = pad(text);
            std::string ciphertext;
            
            CryptoPP::StringSource(paddedText, true, 
                new CryptoPP::StreamTransformationFilter(encryptor,
                    new CryptoPP::StringSink(ciphertext)
                )
            );
            
            // 将IV和密文合并并进行Base64编码
            std::string ivAndCiphertext;
            ivAndCiphertext.append(reinterpret_cast<const char*>(iv), blockSize);
            ivAndCiphertext.append(ciphertext);
            
            std::string encoded;
            CryptoPP::StringSource(ivAndCiphertext, true,
                new CryptoPP::Base64Encoder(
                    new CryptoPP::StringSink(encoded),
                    false  // No newlines
                )
            );
            
            return encoded;
        } 
        else if (mode == "decrypt") {
            try {
                // Base64解码
                std::string decoded;
                CryptoPP::StringSource(text, true,
                    new CryptoPP::Base64Decoder(
                        new CryptoPP::StringSink(decoded)
                    )
                );
                
                // 提取IV
                if (decoded.length() < blockSize) {
                    throw std::runtime_error("Ciphertext too short");
                }
                
                std::string iv = decoded.substr(0, blockSize);
                std::string ciphertext = decoded.substr(blockSize);
                
                // 设置密钥和IV
                CryptoPP::CBC_Mode<CryptoPP::AES>::Decryption decryptor;
                decryptor.SetKeyWithIV((CryptoPP::byte*)paddedKey.data(), paddedKey.size(), 
                                       (CryptoPP::byte*)iv.data());
                
                // 解密
                std::string decrypted;
                CryptoPP::StringSource(ciphertext, true,
                    new CryptoPP::StreamTransformationFilter(decryptor,
                        new CryptoPP::StringSink(decrypted)
                    )
                );
                
                // 去除填充
                return unpad(decrypted);
            } 
            catch (const CryptoPP::Exception& e) {
                throw std::runtime_error(std::string("Decryption failed: ") + e.what());
            }
            catch (const std::exception& e) {
                throw std::runtime_error(std::string("Decryption failed: ") + e.what());
            }
        } 
        else {
            throw std::invalid_argument("Mode must be 'encrypt' or 'decrypt'");
        }
    }
};