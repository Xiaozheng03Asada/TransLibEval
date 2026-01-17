package com.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilterEvenNumbersDask {
    public String check_discount_for_large_order(String data_str) {
        try {
            if (StringUtils.isBlank(data_str)) {
                return "";
            }

            // Convert string to list of integers
            List<String> numbers = Arrays.asList(data_str.split(","));

            // Using RxJava to implement parallel processing similar to dask
            String result = Observable.fromIterable(numbers)
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .filter(num -> num % 2 == 0)
                    .map(String::valueOf)
                    .toList()
                    .subscribeOn(Schedulers.computation())
                    .map(list -> String.join(",", list))
                    .blockingGet();

            return result;
        } catch (Exception e) {
            return "Error";
        }
    }
}