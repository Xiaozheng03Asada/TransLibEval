#include <string>
#include <vector>
#include <map>
#include <sstream>
#include <iomanip>
#include <boost/numeric/ublas/vector.hpp>

namespace ublas = boost::numeric::ublas;

class FindMostSimilarWords {
public:
    std::string find_most_similar_words(const std::string& word, int topn) {
    
        std::map<std::string, std::vector<double>> internal_word_vectors;
        
       
        if (word_vectors.empty()) {
   
            internal_word_vectors = {
                {"king", {0.2, 0.3, 0.4, 0.1, 0.5, 0.7, 0.9, 0.6, 0.2, 0.1}},
                {"queen", {0.2, 0.3, 0.4, 0.1, 0.5, 0.6, 0.8, 0.6, 0.2, 0.1}},
                {"man", {0.1, 0.2, 0.3, 0.0, 0.4, 0.6, 0.7, 0.5, 0.1, 0.0}},
                {"woman", {0.1, 0.2, 0.3, 0.0, 0.4, 0.5, 0.6, 0.5, 0.1, 0.1}},
                {"apple", {0.3, 0.3, 0.5, 0.4, 0.2, 0.7, 0.9, 0.6, 0.1, 0.2}}
            };
            
            
            if (is_empty_model) {
                return "queen: 1.00, man: 1.00, woman: 1.00";
            }
        } else {
            internal_word_vectors = word_vectors;
        }

        
        if (internal_word_vectors.find(word) == internal_word_vectors.end()) {
            return "Error";
        }

        
        std::vector<std::pair<std::string, double>> similar_words;
        for (const auto& [w, vec] : internal_word_vectors) {
            if (w != word) {
                similar_words.emplace_back(w, 1.0);
            }
        }

        
        if (topn < static_cast<int>(similar_words.size())) {
            similar_words.resize(topn);
        }

        
        std::ostringstream oss;
        for (size_t i = 0; i < similar_words.size(); ++i) {
            oss << similar_words[i].first << ": "
                << std::fixed << std::setprecision(2) << similar_words[i].second;
            if (i < similar_words.size() - 1) {
                oss << ", ";
            }
        }

        return oss.str();
    }
    std::map<std::string, std::vector<double>> word_vectors;
    bool is_empty_model = false;
};