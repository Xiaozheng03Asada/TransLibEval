#include <string>
#include <soundex.h>

class SoundexProcessor {
public:
    std::string generate_soundex(const std::string& input_string) {
        if (input_string.empty()) {
            return "The given string is empty.";
        }
        Soundex soundex;
        return soundex.encode(input_string);
    }
};