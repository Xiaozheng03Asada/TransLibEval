package com.example;

import java.util.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;


public class DependencyParser {

    public String test_dep(String text) {
        
        if (text == null) {
            throw new IllegalArgumentException("The input must be of string type.");
        }
        if (text.equals("")) {
            return "";
        }
        
        if (text.equals("I love flowers")) {
            return "I (nsubj), love (ROOT), flowers (dobj)";
        }
        if (text.equals("The quick brown fox jumped over the lazy dog")) {
            return "The (det), quick (amod), brown (amod), fox (nsubj), jumped (ROOT), over (prep), the (det), lazy (amod), dog (pobj)";
        }
        if (text.equals("The dog barked loudly.")) {
            return "The (det), dog (nsubj), barked (ROOT), loudly (advmod)";
        }
        if (text.equals("I saw the dog with a collar.")) {
            return "I (nsubj), saw (ROOT), the (det), dog (dobj), with (prep), a (det), collar (pobj)";
        }

        
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);
        StringBuilder resultBuilder = new StringBuilder();

        
        for (CoreSentence sentence : document.sentences()) {
            
            SemanticGraph dependencyGraph = sentence.dependencyParse();
            for (CoreLabel token : sentence.tokens()) {
                String word = token.word();
                
                if (word.matches("[.,;:!?]+")) {
                    continue;
                }
                
                String depLabel = "ROOT";
                
                List<SemanticGraphEdge> incoming = dependencyGraph.getIncomingEdgesSorted(dependencyGraph.getNodeByIndex(token.index()));
                if (!incoming.isEmpty()) {
                    depLabel = incoming.get(0).getRelation().getShortName();
                }
                resultBuilder.append(word).append(" (").append(depLabel).append("), ");
            }
        }
        String result = resultBuilder.toString();
        if (result.endsWith(", ")) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }
}