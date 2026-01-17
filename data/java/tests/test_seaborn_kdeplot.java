package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_seaborn_kdeplot {

    @Test
    void test_kdeplot_single_data() {
        String data = "1.0,2.0,3.0,4.0,5.0";
        function_seaborn_kdeplot kde_plot = new function_seaborn_kdeplot();
        String result = kde_plot.generate_kdeplot(data, true, "blue");
        assertTrue(result.contains("'data': '1.0,2.0,3.0,4.0,5.0'"));
        assertTrue(result.contains("'shade': true"));
        assertTrue(result.contains("'color': 'blue'"));
    }

    @Test
    void test_kdeplot_with_different_color() {
        String data = "1.0,2.0,3.0,4.0,5.0";
        function_seaborn_kdeplot kde_plot = new function_seaborn_kdeplot();
        String result = kde_plot.generate_kdeplot(data, true, "red");
        assertTrue(result.contains("'data': '1.0,2.0,3.0,4.0,5.0'"));
        assertTrue(result.contains("'shade': true"));
        assertTrue(result.contains("'color': 'red'"));
    }

    @Test
    void test_kdeplot_without_shade() {
        String data = "1.0,2.0,3.0,4.0,5.0";
        function_seaborn_kdeplot kde_plot = new function_seaborn_kdeplot();
        String result = kde_plot.generate_kdeplot(data, false, "green");
        assertTrue(result.contains("'data': '1.0,2.0,3.0,4.0,5.0'"));
        assertTrue(result.contains("'shade': false"));
        assertTrue(result.contains("'color': 'green'"));
    }

    @Test
    void test_kdeplot_empty_data() {
        String data = "";
        function_seaborn_kdeplot kde_plot = new function_seaborn_kdeplot();
        assertThrows(IllegalArgumentException.class, () -> {
            kde_plot.generate_kdeplot(data, true, "blue");
        });
    }

    @Test
    void test_kdeplot_invalid_data() {
        String data = "invalid data";
        function_seaborn_kdeplot kde_plot = new function_seaborn_kdeplot();
        assertThrows(IllegalArgumentException.class, () -> {
            kde_plot.generate_kdeplot(data, true, "blue");
        });
    }
}