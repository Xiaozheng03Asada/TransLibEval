package com.example;

import com.github.slugify.Slugify;

public class TextSlugifier {

    // All functionality is integrated inside this single method.
    // When the delimiter parameter is null, the default "-" is used.
    public String slugify_example(String text, String delimiter) {
        // Validate input: if text is null, throw an exception.
        if (text == null) {
            throw new IllegalArgumentException("Input text must be a string");
        }
        // Use default delimiter "-" when delimiter is null.
        if (delimiter == null) {
            delimiter = "-";
        }
        Slugify slugify;
        try {
            // Using the builder pattern to create an instance of Slugify
            slugify = Slugify.builder().build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize slugify dependency", e);
        }
        // Generate slug using the third-party library.
        String slug = slugify.slugify(text);
        // If a custom delimiter is provided (different from "-"), replace the default delimiter.
        if (!"-".equals(delimiter)) {
            slug = slug.replace("-", delimiter);
        }
        return slug;
    }
}