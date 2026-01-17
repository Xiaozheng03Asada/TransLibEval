#include <libstemmer.h>

class Stemmer {
public:
    std::string test_stem(const std::string& word) {
        sb_stemmer* porterStemmer = sb_stemmer_new("english", NULL);
        const char* stemmed = sb_stemmer_stem(porterStemmer, word.c_str(), word.size());
        std::string result(stemmed);
        sb_stemmer_delete(porterStemmer);
        return result;
    }
};