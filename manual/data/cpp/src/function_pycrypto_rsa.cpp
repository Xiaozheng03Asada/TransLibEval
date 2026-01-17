
#include <string>
#include <cryptopp/rsa.h>
#include <cryptopp/osrng.h>
#include <cryptopp/base64.h>
#include <cryptopp/files.h>
#include <cryptopp/oaep.h>
#include <cryptopp/filters.h>
#include <cryptopp/hex.h>
#include <sstream>
#include <stdexcept>
class RSACrypto
{
public:
    std::string rsa_process(const std::string &text = "",
                            const std::string &mode = "encrypt",
                            int key_size = 2048,
                            const std::string &public_key_str = "",
                            const std::string &private_key_str = "")
    {
        if (mode == "generate_keypair") {
            CryptoPP::AutoSeededRandomPool rng;
            
            // 生成RSA密钥对
            CryptoPP::RSA::PrivateKey privateKey;
            privateKey.GenerateRandomWithKeySize(rng, key_size);
            
            // 从私钥导出公钥
            CryptoPP::RSA::PublicKey publicKey(privateKey);
            
            // 验证密钥有效性
            bool privateKeyValid = privateKey.Validate(rng, 3);
            bool publicKeyValid = publicKey.Validate(rng, 3);
            if (!privateKeyValid || !publicKeyValid) {
                throw std::invalid_argument("Generated keys are not valid");
            }
            
            // 导出密钥为Base64格式
            std::string private_key;
            std::string public_key;
            
            // 导出私钥
            CryptoPP::StringSink private_string_sink(private_key);
            privateKey.DEREncode(private_string_sink);
            std::string private_key_base64;
            CryptoPP::StringSource(private_key, true,
                new CryptoPP::Base64Encoder(
                    new CryptoPP::StringSink(private_key_base64),
                    false // 不插入换行符
                )
            );
            
            // 导出公钥
            CryptoPP::StringSink public_string_sink(public_key);
            publicKey.DEREncode(public_string_sink);
            std::string public_key_base64;
            CryptoPP::StringSource(public_key, true,
                new CryptoPP::Base64Encoder(
                    new CryptoPP::StringSink(public_key_base64),
                    false // 不插入换行符
                )
            );
            
            return private_key_base64 + "\n" + public_key_base64;
        }
        else if (mode == "encrypt") {
            if (text.empty() || public_key_str.empty()) {
                throw std::invalid_argument("Text and public key must be provided for encryption");
            }
            
            try {
                // 检查输入文本是否太大
                if (text.length() > 200) {  // 根据RSA密钥大小和OAEP填充限制，可能需要调整
                    throw std::invalid_argument("Text too large for RSA encryption");
                }
                
                CryptoPP::AutoSeededRandomPool rng;
                
                // 从Base64字符串加载公钥
                std::string decoded_key;
                CryptoPP::StringSource(public_key_str, true,
                    new CryptoPP::Base64Decoder(
                        new CryptoPP::StringSink(decoded_key)
                    )
                );
                
                CryptoPP::StringSource public_key_source(decoded_key, true);
                CryptoPP::RSA::PublicKey publicKey;
                publicKey.BERDecode(public_key_source);
                
                // 验证密钥
                bool valid = publicKey.Validate(rng, 3);
                if (!valid) {
                    throw std::invalid_argument("Invalid public key");
                }
                
                // 创建OAEP加密器
                CryptoPP::RSAES_OAEP_SHA_Encryptor encryptor(publicKey);
                
                // 加密数据
                std::string encrypted;
                CryptoPP::StringSource(text, true,
                    new CryptoPP::PK_EncryptorFilter(rng, encryptor,
                        new CryptoPP::StringSink(encrypted)
                    )
                );
                
                // Base64编码加密后的数据
                std::string encoded;
                CryptoPP::StringSource(encrypted, true,
                    new CryptoPP::Base64Encoder(
                        new CryptoPP::StringSink(encoded),
                        false // 不插入换行符
                    )
                );
                
                return encoded;
            }
            catch (const CryptoPP::Exception& e) {
                throw std::invalid_argument(std::string("Encryption failed: ") + e.what());
            }
        }
        else if (mode == "decrypt") {
            if (text.empty() || private_key_str.empty()) {
                throw std::invalid_argument("Encrypted text and private key must be provided for decryption");
            }
            
            try {
                CryptoPP::AutoSeededRandomPool rng;
                
                // 从Base64字符串加载私钥
                std::string decoded_key;
                CryptoPP::StringSource(private_key_str, true,
                    new CryptoPP::Base64Decoder(
                        new CryptoPP::StringSink(decoded_key)
                    )
                );
                
                CryptoPP::StringSource private_key_source(decoded_key, true);
                CryptoPP::RSA::PrivateKey privateKey;
                privateKey.BERDecode(private_key_source);
                
                // 验证密钥
                bool valid = privateKey.Validate(rng, 3);
                if (!valid) {
                    throw std::invalid_argument("Invalid private key");
                }
                
                // 创建OAEP解密器
                CryptoPP::RSAES_OAEP_SHA_Decryptor decryptor(privateKey);
                
                // Base64解码
                std::string decoded;
                CryptoPP::StringSource(text, true,
                    new CryptoPP::Base64Decoder(
                        new CryptoPP::StringSink(decoded)
                    )
                );
                
                // 解密
                std::string decrypted;
                CryptoPP::StringSource(decoded, true,
                    new CryptoPP::PK_DecryptorFilter(rng, decryptor,
                        new CryptoPP::StringSink(decrypted)
                    )
                );
                
                return decrypted;
            }
            catch (const CryptoPP::Exception& e) {
                throw std::invalid_argument(std::string("Decryption failed: ") + e.what());
            }
        }
        else {
            throw std::invalid_argument("Mode must be 'encrypt', 'decrypt', or 'generate_keypair'");
        }
    }
};