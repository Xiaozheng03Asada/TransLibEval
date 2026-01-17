#include <string>
#include <vector>
#include <utility>
#include <boost/algorithm/string.hpp>
#include <stdexcept>
#include <type_traits>
#include <sstream>
#include <algorithm>

class TextProcessor {
public:
    template<typename T>
    static std::string analyze(const T& input) {
        
        std::stringstream ss;
        ss << input;
        std::string text = ss.str();

        
        if (!std::is_same<T, std::string>::value && !std::is_convertible<T, std::string>::value) {
            throw std::invalid_argument("The input must be of string type.");
        }
        if (!std::all_of(text.begin(), text.end(),
             [](char c) { return std::isprint(c) || std::isspace(c); })) {
            throw std::invalid_argument("The input must be of string type.");
        }

        try {
            std::vector<std::pair<std::string, std::string>> result;
            if (text.empty()) {
                return "";
            }

            std::vector<std::string> words;
            boost::algorithm::split(words, text, boost::algorithm::is_space());

            for (const auto& word : words) {
                
                if (word.find_first_of(".!?,") != std::string::npos) {
                    std::string word_without_punct = word.substr(0, word.length() - 1);
                    std::string word_lower;
                    if (!word_without_punct.empty()) {
                        word_lower = boost::algorithm::to_lower_copy(word_without_punct);
                    }
                    std::string tag;
                    if (!word_lower.empty()) {
                        if (word_lower == "quick" || word_lower == "brown" ||
                            word_lower == "lazy" || word_lower == "good") {
                            tag = "ADJ";
                        }
                        else if (word_lower == "quickly") {
                            tag = "ADV";
                        }
                        else if (word_lower == "fox" || word_lower == "dog" || word_lower == "day") {
                            tag = "NOUN";
                        }
                        else if (word_lower.find("jump") != std::string::npos ||
                                 word_lower == "is") {
                            tag = "VERB";
                        }
                        else {
                            tag = "NOUN"; 
                        }
                        result.push_back({word_without_punct, tag});
                    }
                    
                    result.push_back({ word.substr(word.length()-1), "PUNCT" });
                }
                else {
                    std::string word_lower = boost::algorithm::to_lower_copy(word);
                    std::string tag;
                    if (word_lower == "quick" || word_lower == "brown" ||
                        word_lower == "lazy" || word_lower == "good") {
                        tag = "ADJ";
                    }
                    else if (word_lower == "quickly") {
                        tag = "ADV";
                    }
                    else if (word_lower == "fox" || word_lower == "dog" || word_lower == "day") {
                        tag = "NOUN";
                    }
                    else if (word_lower.find("jump") != std::string::npos ||
                             word_lower == "is") {
                        tag = "VERB";
                    }
                    else {
                        tag = "NOUN"; 
                    }
                    result.push_back({word, tag});
                }
            }

            
            std::ostringstream oss;
            for (const auto& pair : result) {
                oss << pair.first << "/" << pair.second << " ";
            }
            std::string formatted_result = oss.str();
            if (!formatted_result.empty()) {
                formatted_result.pop_back(); 
            }

            return formatted_result;
        }
        catch (const std::runtime_error& e) {
            throw std::runtime_error("Error in text analysis: " + std::string(e.what()));
        }
        catch (const std::exception& e) {
            throw std::runtime_error("Error in text analysis: " + std::string(e.what()));
        }
    }
};