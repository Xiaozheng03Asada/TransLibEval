#include <iostream>
#include <string>
#include <vector>
#include <stdexcept>
#include <stanfordnlp/StanfordCoreNLP.h>
#include <stanfordnlp/Annotation.h>
#include <stanfordnlp/CoreAnnotations.h>
#include <stanfordnlp/CoreLabel.h>
#include <stanfordnlp/CoreMap.h>

class SpacyPosProcessor {
public:
    std::string process(const std::string& text) {
        if (text.empty()) {
            throw std::invalid_argument("The input must be of string type.");
        }

        try {
            // 配置StanfordCoreNLP，添加ssplit注释器
            std::map<std::string, std::string> props = {{"annotators", "tokenize, ssplit, pos"}};
            StanfordCoreNLP pipeline(props);

            // 创建注释对象
            Annotation document(text);
            pipeline.annotate(document);

            // 获取句子列表
            std::vector<CoreMap> sentences = document.get<CoreAnnotations::SentencesAnnotation>();

            // 构建POS标注结果
            std::string posTags;
            for (const auto& sentence : sentences) {
                for (const auto& token : sentence.get<CoreAnnotations::TokensAnnotation>()) {
                    std::string word = token.word();
                    std::string pos = token.get<CoreAnnotations::PartOfSpeechAnnotation>();
                    posTags += word + " (" + pos + "), ";
                }
            }

            // 移除最后的逗号和空格
            if (!posTags.empty()) {
                posTags = posTags.substr(0, posTags.length() - 2);
            }

            return posTags;

        } catch (const std::exception& e) {
            std::cerr << "OtherError: " << e.what() << std::endl;
            throw;
        }
    }
};

class FunctionSpacyPos {
public:
    std::string test_pos(const std::string& text) {
        SpacyPosProcessor processor;
        return processor.process(text);
    }
};