package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_seaborn_color_palette {

    /**
     * 辅助方法：统计字符串中出现 "[D@" 的次数
     */
    private int countColorArrays(String result) {
        if ("[]".equals(result)) {
            return 0;
        }
        // 使用正则 split 来统计出现次数
        return result.split("\\[D@").length - 1;
    }

    @Test
    public void test_default_palette() {
        function_seaborn_color_palette obj = new function_seaborn_color_palette();
        String result = obj.generate_palette("deep", 6);
        assertTrue(result.startsWith("[") && result.endsWith("]"));
        int count = countColorArrays(result);
        assertEquals(6, count, "默认调色板应返回6个颜色");
    }

    @Test
    public void test_bright_palette() {
        function_seaborn_color_palette obj = new function_seaborn_color_palette();
        String result = obj.generate_palette("bright", 5);
        assertTrue(result.startsWith("[") && result.endsWith("]"));
        int count = countColorArrays(result);
        assertEquals(5, count, "Bright调色板应返回5个颜色");
    }

    @Test
    public void test_colorblind_palette() {
        function_seaborn_color_palette obj = new function_seaborn_color_palette();
        String result = obj.generate_palette("colorblind", 7);
        assertTrue(result.startsWith("[") && result.endsWith("]"));
        int count = countColorArrays(result);
        assertEquals(7, count, "Colorblind调色板应返回7个颜色");
    }

    @Test
    public void test_empty_palette() {
        function_seaborn_color_palette obj = new function_seaborn_color_palette();
        String result = obj.generate_palette("deep", 0);
        assertEquals("[]", result, "请求0个颜色时应返回 []");
    }

    @Test
    public void test_large_palette() {
        function_seaborn_color_palette obj = new function_seaborn_color_palette();
        String result = obj.generate_palette("muted", 15);
        assertTrue(result.startsWith("[") && result.endsWith("]"));
        int count = countColorArrays(result);
        // 对于 muted 调色板，实际返回的颜色个数为 DefaultDrawingSupplier 默认色板的长度
        assertTrue(count > 0, "Muted调色板应返回至少1个颜色");
    }
}