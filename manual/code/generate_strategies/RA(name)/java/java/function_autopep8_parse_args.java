package com.example;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ArgumentParser {
    // 唯一的方法 parse_arguments 实现所有功能，不使用 static
    public String parse_arguments(String input_str) {
        // 定义选项，对应 Python 中的 add_argument
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
            // 按空格拆分输入字符串，模拟 Python 的 split() 方法
            String[] args = input_str.trim().split("\\s+");
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            // 如果存在额外参数，则返回错误信息 "Error: 2"
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