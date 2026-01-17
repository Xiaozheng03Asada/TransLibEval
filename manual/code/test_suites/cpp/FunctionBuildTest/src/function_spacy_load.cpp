#include <string>
#include <vector>
#include <stdexcept>
#include <memory>
#include <stanfordnlp/StanfordCoreNLP.h>
#include <stanfordnlp/CoreDocument.h>

class FunctionSpacyLoad {
public:
    std::string spacy_text(const std::string& text) {
        try {
            if (text.empty()) {
                return "Other errors: Input is null";
            }

            std::unique_ptr<StanfordCoreNLP> pipeline;
            {
                std::map<std::string, std::string> props;
                props["annotators"] = "tokenize";
                pipeline = std::make_unique<StanfordCoreNLP>(props);
            }

            CoreDocument doc(text);
            pipeline->annotate(doc);

            std::string result;
            for (const auto& token : doc.tokens()) {
                result += token.word() + " ";
            }
            if (!result.empty()) {
                result.pop_back(); // Remove the trailing space
            }
            return result;
        } catch (const std::bad_alloc& e) {
            return "Insufficient memory error";
        } catch (const std::runtime_error& e) {
            if (std::string(e.what()).find("Model file not found") != std::string::npos) {
                return "Model file not found error";
            } else if (std::string(e.what()).find("UnicodeDecodeError") != std::string::npos) {
                return "UnicodeDecodeError: " + std::string(e.what());
            } else {
                return "Other errors: " + std::string(e.what());
            }
        } catch (const std::exception& e) {
            return "Other errors: " + std::string(e.what());
        }
    }
};