#include <string>
#include <soundex.h>

class SoundexProcessor {
public:
    static std::string compare_strings(const std::string& str1, const std::string& str2) {
        Soundex soundex;
        if (str1.empty() || str2.empty()) {
            return "The given string is empty.";
        }
        return soundex.encode(str1) == soundex.encode(str2) ? "true" : "false";
    }
};