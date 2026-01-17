package com.example;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ArgumentParser {
    
    public String parse_arguments(String input_str) {
        
        Options options = new Options();
        options.addOption(Option.builder("n")
                .longOpt("name")
                .hasArg(true)
                .required(true)
                .desc("Your name")
                .build());
        options.addOption(Option.builder("a")
                .longOpt("age")
                .hasArg(true)
                .required(true)
                .desc("Your age")
                .build());
        options.addOption(Option.builder("c")
                .longOpt("city")
                .hasArg(true)
                .required(false)
                .desc("Your city")
                .build());

        try {
            
            String[] args = input_str.trim().split("\\s+");
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            
            String[] remaining = cmd.getArgs();
            if (remaining.length > 0) {
                return "Error: 2";
            }

            String name = cmd.getOptionValue("n");
            String age = cmd.getOptionValue("a");
            String city = cmd.getOptionValue("c");
            if (city == null) {
                city = "Not provided";
            }
            return "Name: " + name + ", Age: " + age + ", City: " + city;
        } catch (ParseException e) {
            return "Error: " + e.getMessage();
        }
    }
}