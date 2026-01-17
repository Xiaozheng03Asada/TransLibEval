package com.example;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.util.FastMath;

public class SeabornCubehelixPalette {
    public String generate_cubehelix(int n_colors, double start, double rot, double gamma) {
        class SeabornCubehelixPalette {
            private double[] create_color(double h, double s, double l) {
                if (s == 0) {
                    return new double[]{l, l, l};
                }

                double function = (l < 0.5) ? l * (1 + s) : l + s - l * s;
                double p2 = 2 * l - function;

                return new double[]{
                        hue_to_rgb(p2, function, h + 1.0/3.0),
                        hue_to_rgb(p2, function, h),
                        hue_to_rgb(p2, function, h - 1.0/3.0)
                };
            }

            private double hue_to_rgb(double p, double q, double t) {
                if (t < 0) t += 1;
                if (t > 1) t -= 1;
                if (t < 1.0/6.0) return p + (q - p) * 6 * t;
                if (t < 1.0/2.0) return q;
                if (t < 2.0/3.0) return p + (q - p) * (2.0/3.0 - t) * 6;
                return p;
            }

            public List<double[]> generate(int n_colors, double start, double rot, double gamma) {
                List<double[]> colors = new ArrayList<>();
                if (n_colors <= 0) {
                    return colors;
                }

                for (int i = 0; i < n_colors; i++) {
                    double fract = (double) i / (n_colors - 1);
                    double angle = 2 * Math.PI * (start / 3.0 + rot * fract);
                    double l = FastMath.pow(fract, gamma);

                    double x = l + 0.5 * FastMath.cos(angle);
                    double y = l + 0.5 * FastMath.sin(angle);

                    colors.add(create_color(angle / (2 * Math.PI), 0.5, l));
                }

                return colors;
            }

            public int countColorsInResult(String result) {
                return (int) result.chars().filter(ch -> ch == '(').count();
            }
        }

        SeabornCubehelixPalette palette = new SeabornCubehelixPalette();
        List<double[]> colors = palette.generate(n_colors, start, rot, gamma);

        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < colors.size(); i++) {
            double[] color = colors.get(i);
            result.append("(")
                    .append(String.format("%.6f", color[0])).append(", ")
                    .append(String.format("%.6f", color[1])).append(", ")
                    .append(String.format("%.6f", color[2])).append(")");
            if (i < colors.size() - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        String finalResult = result.toString();

        
        int actualColors = palette.countColorsInResult(finalResult);
        if (actualColors != n_colors && finalResult.equals("[]")) {
            return "[]";
        } else if (actualColors != n_colors) {
            throw new IllegalStateException("Generated colors count does not match requested count");
        }

        return finalResult;
    }
}