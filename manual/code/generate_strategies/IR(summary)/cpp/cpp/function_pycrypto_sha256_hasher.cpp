#include <string>
#include <cryptopp/sha.h>
#include <cryptopp/hex.h>
#include <cryptopp/filters.h>
class SHA256Hasher {
public:
    std::string hash_text(const std::string& text){
        CryptoPP::SHA256 hash;
        std::string digest;
        
        CryptoPP::StringSource s(text, true,
            new CryptoPP::HashFilter(hash,
                new CryptoPP::HexEncoder(
                    new CryptoPP::StringSink(digest)
                )
            )
        );
        
        return digest;
    }
};