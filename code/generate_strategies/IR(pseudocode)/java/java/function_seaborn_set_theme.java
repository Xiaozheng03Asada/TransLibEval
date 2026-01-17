package com.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.renderer.category.StandardBarPainter;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class SeabornThemeSetter {
    public String set_theme(String style, String context, String palette) {
        class SeabornThemeSetter {
            private List<String> valid_styles = Arrays.asList("white", "dark", "whitegrid", "darkgrid", "ticks");
            private List<String> valid_contexts = Arrays.asList("paper", "notebook", "talk", "poster");
            private List<String> valid_palettes = Arrays.asList("deep", "muted", "bright", "pastel", "dark", "colorblind");

            public String setTheme(String style, String context, String palette) {
                if (!valid_styles.contains(style)) {
                    style = "darkgrid";
                }
                if (!valid_contexts.contains(context)) {
                    context = "notebook";
                }
                if (!valid_palettes.contains(palette)) {
                    palette = "deep";
                }

                
                StandardChartTheme theme = new StandardChartTheme("Seaborn-like");
                if (style.equals("dark") || style.equals("darkgrid")) {
                    theme.setPlotBackgroundPaint(Color.DARK_GRAY);
                }

                return String.format("{'style': '%s', 'context': '%s', 'palette': '%s'}", style, context, palette);
            }
        }

        SeabornThemeSetter themeSetter = new SeabornThemeSetter();
        return themeSetter.setTheme(style, context, palette);
    }
}