package com.example;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.collections4.CollectionUtils;
import java.util.*;

public class TransactionEncoderWrapper {
    public String encode_transactions(String transactions) {
        
        class TransactionEncoderWrapper {
            public String encode(String transactions) {
                if (transactions == null || transactions.trim().isEmpty()) {
                    return "[]";
                }

                
                String[] transactionArray = transactions.split(";");

                
                Set<String> uniqueItems = new LinkedHashSet<>();

                
                List<List<String>> transactionsList = new ArrayList<>();

                
                for (String transaction : transactionArray) {
                    String[] items = transaction.split(",");
                    List<String> itemList = Arrays.asList(items);
                    transactionsList.add(itemList);
                    for (String item : items) {
                        uniqueItems.add(item);
                    }
                }

                
                List<String> uniqueItemsList = new ArrayList<>(uniqueItems);

                
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < transactionsList.size(); i++) {
                    List<String> transaction = transactionsList.get(i);
                    Set<String> transactionSet = new HashSet<>(transaction);

                    for (int j = 0; j < uniqueItemsList.size(); j++) {
                        result.append(transactionSet.contains(uniqueItemsList.get(j)) ? "1" : "0");
                        if (j < uniqueItemsList.size() - 1) {
                            result.append(",");
                        }
                    }
                    if (i < transactionsList.size() - 1) {
                        result.append(";");
                    }
                }

                return result.toString();
            }
        }

        
        TransactionEncoderWrapper encoder = new TransactionEncoderWrapper();
        return encoder.encode(transactions);
    }
}