#include <string>
#include <vector>
#include <map>
#include <cmath>
#include <sstream>
#include <algorithm>

class SpacyVectorProcessor {
private:
    // Assuming StanfordCoreNLP is replaced with a similar C++ library or custom implementation
    // For simplicity, we'll use a placeholder for the pipeline
    std::map<std::string, std::string> pipeline;

    double calculateDeterministicVectorLength(const std::string& word) {
        int hash = std::hash<std::string>{}(word);
        double base = std::abs(hash) % 1000 / 100.0;
        double caseFactor = (word == toLowerCase(word)) ? 1.0 : 1.2;
        return base * caseFactor;
    }

    std::string toLowerCase(const std::string& str) {
        std::string lowerStr = str;
        std::transform(lowerStr.begin(), lowerStr.end(), lowerStr.begin(), ::tolower);
        return lowerStr;
    }

public:
    SpacyVectorProcessor() {
        // Initialize pipeline with properties (placeholder)
        pipeline["annotators"] = "tokenize,ssplit,pos,lemma";
    }

    std::string processText(const std::string& text) {
        // Placeholder for document annotation
        std::map<std::string, std::vector<std::map<std::string, std::string>>> document;
        // Placeholder for sentences
        std::vector<std::map<std::string, std::vector<std::map<std::string, std::string>>>> sentences;

        std::vector<std::string> vectorLengths;
        for (const auto& sentence : sentences) {
            for (const auto& token : sentence.at("tokens")) {
                double vectorLength = calculateDeterministicVectorLength(token.at("word"));
                std::ostringstream oss;
                oss << token.at("word") << ": " << std::fixed << std::setprecision(6) << vectorLength;
                vectorLengths.push_back(oss.str());
            }
        }

        std::ostringstream result;
        for (size_t i = 0; i < vectorLengths.size(); ++i) {
            if (i != 0) {
                result << ", ";
            }
            result << vectorLengths[i];
        }

        return result.str();
    }
};

class FunctionSpacyVector {
public:
    std::string vector_lengths(const std::string& text) {
        SpacyVectorProcessor processor;
        return processor.processText(text);
    }
};