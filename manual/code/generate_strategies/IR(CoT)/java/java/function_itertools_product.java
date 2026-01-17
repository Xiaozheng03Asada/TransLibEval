package com.example;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

public class CartesianProductProcessor {
    public String test_product(String input_string) {
        return new Object() {
            private class CartesianProductProcessor {
                public String process(String input) {
                    if (input == null) {
                        throw new IllegalArgumentException();
                    }

                    
                    if (!input.contains(";") && !input.contains(",")) {
                        throw new IllegalArgumentException();
                    }

                    try {
                        List<List<String>> lists = new ArrayList<>();
                        List<String> currentList = new ArrayList<>();
                        StringBuilder currentElement = new StringBuilder();

                        for (char c : input.toCharArray()) {
                            if (c == ',') {
                                if (currentElement.length() > 0) {
                                    currentList.add(currentElement.toString());
                                    currentElement = new StringBuilder();
                                }
                            } else if (c == ';') {
                                if (currentElement.length() > 0) {
                                    currentList.add(currentElement.toString());
                                    currentElement = new StringBuilder();
                                }
                                if (!currentList.isEmpty()) {
                                    lists.add(new ArrayList<>(currentList));
                                    currentList = new ArrayList<>();
                                } else {
                                    throw new IllegalArgumentException();
                                }
                            } else {
                                currentElement.append(c);
                            }
                        }

                        if (currentElement.length() > 0) {
                            currentList.add(currentElement.toString());
                        }
                        if (!currentList.isEmpty()) {
                            lists.add(new ArrayList<>(currentList));
                        }
                        if (lists.isEmpty()) {
                            throw new IllegalArgumentException();
                        }

                        List<List<String>> cartesianProduct = Lists.cartesianProduct(lists);
                        List<String> result = new ArrayList<>();
                        for (List<String> combination : cartesianProduct) {
                            result.add(String.join("", combination));
                        }
                        return String.join(";", result);
                    } catch (Exception e) {
                        throw new IllegalArgumentException();
                    }
                }
            }

            public String execute() {
                return new CartesianProductProcessor().process(input_string);
            }
        }.execute();
    }
}