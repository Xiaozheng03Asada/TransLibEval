package com.example;

import org.apache.commons.codec.language.RefinedSoundex;

public class SoundexProcessor {
    public int compute_distance(String word1, String word2, String metric) {
        class SoundexProcessor {
            public int compute_distance(String word1, String word2, String metric) {
                if (metric == null || word1 == null || word2 == null) {
                    throw new IllegalArgumentException("Input parameters cannot be null");
                }

                if ("refined".equals(metric)) {
                    RefinedSoundex rs = new RefinedSoundex();
                    String encoding1 = rs.encode(word1);
                    String encoding2 = rs.encode(word2);
                    int difference = 0;
                    for (int i = 0; i < Math.min(encoding1.length(), encoding2.length()); i++) {
                        if (encoding1.charAt(i) != encoding2.charAt(i)) {
                            difference++;
                        }
                    }
                    return difference;
                } else if ("hamming".equals(metric)) {
                    // Python的pyphonetics库似乎对Hamming距离有特殊处理
                    // 如果字符串不完全相同，返回1
                    return word1.equals(word2) ? 0 : 1;
                } else {
                    throw new IllegalArgumentException("Invalid metric. Only 'refined' and 'hamming' are supported.");
                }
            }
        }
        return new SoundexProcessor().compute_distance(word1, word2, metric);
    }
}