package com.example;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Paint;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;

public class SeabornColorPalette {
    public String generate_palette(String palette_name, int n_colors) {
        class SeabornColorPalette {
            public String generatePalette(String paletteName, int nColors) {
                if (nColors == 0) {
                    return "[]";
                }

                Color[] colors;
                switch (paletteName.toLowerCase()) {
                    case "bright":
                        colors = new Color[]{
                                new Color(0, 114, 178),
                                new Color(230, 159, 0),
                                new Color(0, 158, 115),
                                new Color(204, 121, 167),
                                new Color(213, 94, 0)
                        };
                        break;
                    case "colorblind":
                        colors = new Color[]{
                                new Color(0, 114, 178),
                                new Color(230, 159, 0),
                                new Color(0, 158, 115),
                                new Color(204, 121, 167),
                                new Color(213, 94, 0),
                                new Color(86, 180, 233),
                                new Color(204, 204, 204)
                        };
                        break;
                    case "muted":
                        Paint[] paints = DefaultDrawingSupplier.DEFAULT_PAINT_SEQUENCE;
                        colors = new Color[paints.length];
                        for (int i = 0; i < paints.length; i++) {
                            if (paints[i] instanceof Color) {
                                colors[i] = (Color) paints[i];
                            } else {
                                
                                colors[i] = Color.GRAY;
                            }
                        }
                        break;
                    default: 
                        colors = new Color[]{
                                new Color(76, 114, 176),
                                new Color(221, 132, 82),
                                new Color(85, 168, 104),
                                new Color(196, 78, 82),
                                new Color(129, 114, 178),
                                new Color(147, 120, 96)
                        };
                }

                List<double[]> palette = new ArrayList<>();
                for (int i = 0; i < nColors && i < colors.length; i++) {
                    Color color = colors[i];
                    palette.add(new double[]{
                            color.getRed() / 255.0,
                            color.getGreen() / 255.0,
                            color.getBlue() / 255.0
                    });
                }
                return palette.toString();
            }
        }

        SeabornColorPalette instance = new SeabornColorPalette();
        return instance.generatePalette(palette_name == null ? "deep" : palette_name, n_colors);
    }
}