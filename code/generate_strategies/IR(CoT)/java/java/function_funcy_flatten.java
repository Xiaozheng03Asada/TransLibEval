package com.example;

import com.google.common.collect.Lists;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ListProcessor {
    public String process_list(String elements) {
        class ListProcessor {
            public String process(String elements) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    Object parsed = mapper.readValue(elements, Object.class);

                    if (!(parsed instanceof List || parsed instanceof Object[])) {
                        return "Error: input is not iterable";
                    }

                    List<Object> result = new ArrayList<>();
                    if (parsed instanceof List) {
                        flatten((List<?>) parsed, result);
                    } else {
                        flatten(Arrays.asList((Object[]) parsed), result);
                    }

                    
                    StringBuilder sb = new StringBuilder();
                    sb.append("[");
                    for (int i = 0; i < result.size(); i++) {
                        Object item = result.get(i);
                        if (item instanceof String) {
                            sb.append("\"").append(item).append("\"");
                        } else {
                            sb.append(item);
                        }
                        if (i < result.size() - 1) {
                            sb.append(", ");
                        }
                    }
                    sb.append("]");
                    return sb.toString();

                } catch (Exception e) {
                    return "Error: invalid input";
                }
            }

            void flatten(List<?> list, List<Object> result) {
                for (Object item : list) {
                    if (item instanceof List || item instanceof Object[]) {
                        if (item instanceof List) {
                            flatten((List<?>) item, result);
                        } else {
                            flatten(Arrays.asList((Object[]) item), result);
                        }
                    } else {
                        result.add(item);
                    }
                }
            }
        }
        return new ListProcessor().process(elements);
    }
}