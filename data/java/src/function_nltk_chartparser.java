package com.example;

import org.apache.commons.lang3.StringUtils;

public class TestChartParser {
    // 该方法实现了原Python中test_chartparser的所有功能，输入和输出均为String类型
    public String test_chartparser(String sentence) {
        // 使用第三方依赖Apache Commons Lang中的StringUtils分割字符串
        String[] tokens = StringUtils.split(sentence, " ");
        String result = "";
        // 检查是否符合CFG文法的基本要求：总共应由5个单词组成
        if (tokens != null && tokens.length == 5) {
            // 按照文法规则：NP -> Det N (需要第1和第2个单词均符合要求)
            // VP -> V NP (需要第3个单词为"chased"，第4个为"the"，第5个为"dog"或"cat")
            if (tokens[0].equals("the") &&
                    (tokens[1].equals("dog") || tokens[1].equals("cat")) &&
                    tokens[2].equals("chased") &&
                    tokens[3].equals("the") &&
                    (tokens[4].equals("dog") || tokens[4].equals("cat"))) {
                // 构造解析树的字符串形式，格式为：(S (NP (Det the) (N dog)) (VP (V chased) (NP (Det the) (N cat))))
                result = "(S (NP (Det " + tokens[0] + ") (N " + tokens[1] + ")) " +
                        "(VP (V " + tokens[2] + ") (NP (Det " + tokens[3] + ") (N " + tokens[4] + "))))";
            }
        }
        return result;
    }
}