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
        
        std::string paddedKey = key;
        paddedKey.resize(32, ' ');
        paddedKey = paddedKey.substr(0, 32);
        
        const int blockSize = CryptoPP::AES::BLOCKSIZE;
        
        
        auto pad = [blockSize](const std::string& text) {
            int paddingLength = blockSize - (text.length() % blockSize);
            std::string result = text;
            for (int i = 0; i < paddingLength; i++) {
                result.push_back(static_cast<char>(paddingLength));
            }
            return result;
        };
        
        
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
            
            CryptoPP::AutoSeededRandomPool rng;
            CryptoPP::byte iv[blockSize]; 
            rng.GenerateBlock(iv, blockSize);
            
            
            CryptoPP::CBC_Mode<CryptoPP::AES>::Encryption encryptor;
            encryptor.SetKeyWithIV((CryptoPP::byte*)paddedKey.data(), paddedKey.size(), iv);
            
            
            std::string paddedText = pad(text);
            std::string ciphertext;
            
            CryptoPP::StringSource(paddedText, true, 
                new CryptoPP::StreamTransformationFilter(encryptor,
                    new CryptoPP::StringSink(ciphertext)
                )
            );
            
            
            std::string ivAndCiphertext;
            ivAndCiphertext.append(reinterpret_cast<const char*>(iv), blockSize);
            ivAndCiphertext.append(ciphertext);
            
            std::string encoded;
            CryptoPP::StringSource(ivAndCiphertext, true,
                new CryptoPP::Base64Encoder(
                    new CryptoPP::StringSink(encoded),
                    false  
                )
            );
            
            return encoded;
        } 
        else if (mode == "decrypt") {
            try {
                
                std::string decoded;
                CryptoPP::StringSource(text, true,
                    new CryptoPP::Base64Decoder(
                        new CryptoPP::StringSink(decoded)
                    )
                );
                
                
                if (decoded.length() < blockSize) {
                    throw std::runtime_error("Ciphertext too short");
                }
                
                std::string iv = decoded.substr(0, blockSize);
                std::string ciphertext = decoded.substr(blockSize);
                
                
                CryptoPP::CBC_Mode<CryptoPP::AES>::Decryption decryptor;
                decryptor.SetKeyWithIV((CryptoPP::byte*)paddedKey.data(), paddedKey.size(), 
                                       (CryptoPP::byte*)iv.data());
                
                
                std::string decrypted;
                CryptoPP::StringSource(ciphertext, true,
                    new CryptoPP::StreamTransformationFilter(decryptor,
                        new CryptoPP::StringSink(decrypted)
                    )
                );
                
                
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