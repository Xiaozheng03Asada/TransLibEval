package com.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Collections;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;

public class Word2VecModelTrainer {
    // 该方法实现所有的功能，输入和输出严格遵守 int、String 等简单数据类型
    public String train_word2vec_model(String sentences_str, int vector_size, int window, int min_count) {
        try {
            // 将输入字符串按";"分割为多个句子，再逐句按空格进行分割实现功能等同于python的 sentence.split()
            String[] sentenceArr = sentences_str.split(";");
            List<String> sentencesList = new ArrayList<>();
            for (String sentence : sentenceArr) {
                if (!sentence.trim().isEmpty()) {
                    sentencesList.add(sentence.trim());
                }
            }
            // 如果没有有效的句子，则返回 "Training failed"
            if (sentencesList.isEmpty()) {
                return "Training failed";
            }
            // 构造句子迭代器，相当于 python 中的句子列表
            CollectionSentenceIterator iterator = new CollectionSentenceIterator(sentencesList);
            // 构造分词器
            DefaultTokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
            // 构建 Word2Vec 模型，与 gensim 中的 Word2Vec 类似功能
            Word2Vec vec = new Word2Vec.Builder()
                    .minWordFrequency(min_count)
                    .layerSize(vector_size)
                    .windowSize(window)
                    .seed(42) // 固定种子保证结果可复现
                    .iterate(iterator)
                    .tokenizerFactory(tokenizerFactory)
                    .build();
            // 开始训练模型
            vec.fit();
            // 获取训练后的词汇列表，逻辑上与 gensim 的 model.wv.index_to_key 等价
            Collection<String> vocabWords = vec.getVocab().words();
            if (vocabWords == null || vocabWords.isEmpty()) {
                return "Training failed";
            }
            List<String> wordsList = new ArrayList<>(vocabWords);
            // 此处不对顺序进行特殊处理，直接按模型原有词序返回（也可选择排序）
            return String.join("|", wordsList);
        } catch (Exception e) {
            return "Training failed";
        }
    }
}