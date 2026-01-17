#include <string>
#include <stdexcept>
#include <algorithm>
#include "slugify.h" // Assuming a similar library exists in C++

class TextSlugifier {
public:
    std::string slugify_example(const std::string& text, const std::string& delimiter) {
        if (text.empty()) {
            throw std::invalid_argument("Input text must be a string");
        }
        std::string actualDelimiter = delimiter.empty() ? "-" : delimiter;
        Slugify slugify;
        try {
            slugify = Slugify::builder().build();
        } catch (const std::exception& e) {
            throw std::runtime_error("Failed to initialize slugify dependency");
        }
        std::string slug = slugify.slugify(text);
        if (actualDelimiter != "-") {
            std::replace(slug.begin(), slug.end(), '-', actualDelimiter[0]);
        }
        return slug;
    }
};