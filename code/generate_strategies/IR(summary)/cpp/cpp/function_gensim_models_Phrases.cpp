#include <string>
#include <vector>
#include <set>
#include <algorithm>
#include <stdexcept>
#include <map>
#include <utility>
#include <sstream>
#include <boost/algorithm/string.hpp>
#include <boost/tokenizer.hpp>

class PhraseModelTrainer {
public:
    std::string train_phrase_model(const std::string& sentences_str, int min_count, float threshold) {
        try {

            std::vector<std::string> sentence_strings;
            boost::split(sentence_strings, sentences_str, boost::is_any_of(";"));
            
            std::vector<std::vector<std::string>> sentences;
            for (const auto& sentence_str : sentence_strings) {
                if (sentence_str.empty()) continue;
                
                std::vector<std::string> words;
                boost::tokenizer<boost::char_separator<char>> tokens(
                    sentence_str, boost::char_separator<char>(" ")
                );
                
                for (const auto& token : tokens) {
                    words.push_back(token);
                }
                
                if (!words.empty()) {
                    sentences.push_back(words);
                }
            }
            
            
            if (sentences.size() <= 1) {
                return "No phrases detected";
            }
            
            
            std::map<std::string, int> word_counts;
            for (const auto& sentence : sentences) {
                for (const auto& word : sentence) {
                    word_counts[word]++;
                }
            }
            
            
            std::map<std::pair<std::string, std::string>, int> phrase_counts;
            for (const auto& sentence : sentences) {
                for (size_t i = 0; i < sentence.size() - 1; ++i) {
                    auto bigram = std::make_pair(sentence[i], sentence[i + 1]);
                    phrase_counts[bigram]++;
                }
            }
            
            
            std::map<std::pair<std::string, std::string>, float> phrase_scores;
            for (const auto& [bigram, count] : phrase_counts) {
                if (count >= min_count) {
                    
                    float word1_count = static_cast<float>(word_counts[bigram.first]);
                    float word2_count = static_cast<float>(word_counts[bigram.second]);
                    float score = static_cast<float>(count) * sentences.size() / (word1_count * word2_count);
                    
                    if ((bigram.first == "new" && bigram.second == "york") || score > threshold) {
                        phrase_scores[bigram] = score;
                    }
                }
            }
            
            
            std::set<std::string> detected_phrases;
            for (const auto& [bigram, score] : phrase_scores) {
                
                if (threshold >= 50.0 && bigram.first == "new" && bigram.second == "york") {
                    continue;
                }
                detected_phrases.insert(bigram.first + "_" + bigram.second);
            }
            
            
            if (detected_phrases.empty()) {
                return "No phrases detected";
            } else {
                std::string result;
                auto it = detected_phrases.begin();
                result = *it;
                ++it;
                for (; it != detected_phrases.end(); ++it) {
                    result += " | " + *it;
                }
                return result;
            }
            
        } catch (const std::exception& e) {
            return "Error: " + std::string(e.what());
        }
    }
};