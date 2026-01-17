package com.example;

import com.github.slugify.Slugify;

public class TextSlugifier {

    
    
    public String slugify_example(String text, String delimiter) {
        
        if (text == null) {
            throw new IllegalArgumentException("Input text must be a string");
        }
        
        if (delimiter == null) {
            delimiter = "-";
        }
        Slugify slugify;
        try {
            
            slugify = Slugify.builder().build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize slugify dependency", e);
        }
        
        String slug = slugify.slugify(text);
        
        if (!"-".equals(delimiter)) {
            slug = slug.replace("-", delimiter);
        }
        return slug;
    }
}