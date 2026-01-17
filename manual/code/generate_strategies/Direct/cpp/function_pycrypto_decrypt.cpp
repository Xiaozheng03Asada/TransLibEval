#include <string>
#include <cryptopp/aes.h>
#include <cryptopp/modes.h>
#include <cryptopp/filters.h>
#include <cryptopp/base64.h>
#include <cryptopp/osrng.h>
#include <stdexcept>
class AESCipher {
public:
    static std::string process(const std::string& key, const std::string& text, const std::string& mode = "encrypt") {
        // 准备密钥 - 填充或截断至32字节
        std::string paddedKey = key;
        paddedKey.resize(32, ' ');
        paddedKey = paddedKey.substr(0, 32);
        
        const int blockSize = CryptoPP::AES::BLOCKSIZE;
        
        if (mode == "encrypt") {
            // 使用PKCS#7填充
            int paddingLength = blockSize - (text.length() % blockSize);
            std::string paddedText = text;
            for (int i = 0; i < paddingLength; i++) {
                paddedText.push_back(static_cast<char>(paddingLength));
            }
            
            // 生成随机IV
            CryptoPP::AutoSeededRandomPool rng;
            CryptoPP::byte iv[blockSize];
            rng.GenerateBlock(iv, blockSize);
            
            // 设置密钥和IV
            CryptoPP::CBC_Mode<CryptoPP::AES>::Encryption encryptor;
            encryptor.SetKeyWithIV((CryptoPP::byte*)paddedKey.data(), paddedKey.size(), iv);
            
            // 加密
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
                    throw std::invalid_argument("Ciphertext too short");
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
                if (decrypted.empty()) {
                    throw std::invalid_argument("Empty decrypted text");
                }
                
                unsigned char paddingLength = static_cast<unsigned char>(decrypted[decrypted.length() - 1]);
                if (paddingLength > blockSize) {
                    throw std::invalid_argument("Invalid padding");
                }
                
                return decrypted.substr(0, decrypted.length() - paddingLength);
            } 
            catch (const CryptoPP::Exception& e) {
                throw std::invalid_argument(std::string("Decryption failed: ") + e.what());
            }
            catch (const std::exception& e) {
                throw std::invalid_argument(std::string("Decryption failed: ") + e.what());
            }
        } 
        else {
            throw std::invalid_argument("Mode must be 'encrypt' or 'decrypt'");
        }
    }
};