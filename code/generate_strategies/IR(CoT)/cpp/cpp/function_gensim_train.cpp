#include <string>
#include <vector>
#include <sstream>
#include <fstream>
#include <stdexcept>
#include <algorithm>
#include <fasttext/fasttext.h>

class Word2VecModelTrainer {
public:
    std::string train_word2vec_model(const std::string& sentences_str, int vector_size, int window, int min_count) {
        try {
            
            std::vector<std::string> sentences;
            std::istringstream iss(sentences_str);
            std::string token;
            while (std::getline(iss, token, ';')) {
                if (!token.empty()) {
                    sentences.push_back(token);
                }
            }
            if (sentences.empty()) {
                return "Training failed";
            }

            
            std::string temp_file = "temp_sentences.txt";
            std::ofstream ofs(temp_file);
            if (!ofs.is_open()) {
                throw std::runtime_error("Failed to create temporary file");
            }
            for (const auto& sentence : sentences) {
                ofs << sentence << "\n";
            }
            ofs.close();

            
            fasttext::FastText model;
            fasttext::Args args;
            args.model = fasttext::model_name::cbow;
            args.dim = vector_size;
            args.ws = window;
            args.minCount = min_count;
            args.input = temp_file;
            args.output = "word2vec_model";

            
            model.train(args);

            
            auto dictionary = model.getDictionary(); 
            if (!dictionary) {
                return "Training failed";
            }

            std::ostringstream oss;
            for (int i = 0; i < dictionary->nwords(); ++i) {
                oss << dictionary->getWord(i);
                if (i < dictionary->nwords() - 1) {
                    oss << "|";
                }
            }

            
            std::remove(temp_file.c_str());

            
            return oss.str();
        } catch (const std::exception&) {
            return "Training failed";
        }
    }
};