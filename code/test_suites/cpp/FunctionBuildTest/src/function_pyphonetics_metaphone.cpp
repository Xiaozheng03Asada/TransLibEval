#include <string>
#include <DoubleMetaphone.h>

class PhoneticProcessor {
public:
    static std::string generate_phonetics(const std::string& word) {
        DoubleMetaphone metaphone;
        if (word.empty()) {
            return "The given string is empty.";
        }
        metaphone.setMaxCodeLen(20);
        return metaphone.encode(word);
    }
};