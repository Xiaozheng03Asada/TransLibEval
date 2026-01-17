package com.example;

import com.google.common.collect.Queues; 
import java.util.Deque;
import java.util.ArrayList;
import java.util.List;

public class DequeOperations {

    
    public String perform_operation(String operation_type) {
        
        Deque<Integer> d = Queues.newArrayDeque();

        if ("append_and_appendleft".equals(operation_type)) {
            
            d.addLast(1);
            
            d.addFirst(0);
            
            List<Integer> list = new ArrayList<>(d);
            
            return list.get(0).toString() + ", " + list.get(1).toString();
        } else if ("pop_and_popleft".equals(operation_type)) {
            
            d.clear();
            d.addLast(1);
            d.addLast(2);
            d.addLast(3);
            
            d.removeFirst();
            
            d.removeLast();
            
            return Integer.toString(d.size());
        } else if ("remove".equals(operation_type)) {
            
            d.clear();
            d.addLast(1);
            d.addLast(2);
            d.addLast(3);
            
            d.remove(2);
            List<Integer> list = new ArrayList<>(d);
            
            return list.get(0).toString() + ", " + list.get(1).toString();
        } else if ("clear".equals(operation_type)) {
            
            d.clear();
            d.addLast(1);
            d.addLast(2);
            d.addLast(3);
            
            d.clear();
            
            return Integer.toString(d.size());
        } else {
            
            throw new IllegalArgumentException("Invalid operation type");
        }
    }
}