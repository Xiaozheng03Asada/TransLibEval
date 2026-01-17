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
            // 将输入字符串分割为句子
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

            // 写入临时文件以供 fastText 使用
            std::string temp_file = "temp_sentences.txt";
            std::ofstream ofs(temp_file);
            if (!ofs.is_open()) {
                throw std::runtime_error("Failed to create temporary file");
            }
            for (const auto& sentence : sentences) {
                ofs << sentence << "\n";
            }
            ofs.close();

            // 配置 fastText 参数
            fasttext::FastText model;
            fasttext::Args args;
            args.model = fasttext::model_name::cbow;
            args.dim = vector_size;
            args.ws = window;
            args.minCount = min_count;
            args.input = temp_file;
            args.output = "word2vec_model";

            // 训练模型
            model.train(args);

            // 获取词汇表
            auto dictionary = model.getDictionary(); // 使用 shared_ptr
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

            // 删除临时文件
            std::remove(temp_file.c_str());

            // 返回词汇表
            return oss.str();
        } catch (const std::exception&) {
            return "Training failed";
        }
    }
};