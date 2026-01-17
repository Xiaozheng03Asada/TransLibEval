package com.example;

import org.jtransforms.fft.DoubleFFT_1D;
import org.jtransforms.fft.DoubleFFT_2D;
import org.jtransforms.fft.DoubleFFT_3D;

public class FFTNProcessor {
    public String compute_fftn(String input_array) {
        try {
            
            String trimmed = input_array.trim();
            
            class Parser {
                String s;
                int pos;

                Parser(String s) {
                    this.s = s;
                    this.pos = 0;
                }

                void skipWhitespace() {
                    while (pos < s.length() && Character.isWhitespace(s.charAt(pos))) {
                        pos++;
                    }
                }

                
                Object parseValue() throws Exception {
                    skipWhitespace();
                    if (pos < s.length() && s.charAt(pos) == '[') {
                        return parseArray();
                    } else {
                        int start = pos;
                        while (pos < s.length() && s.charAt(pos) != ',' && s.charAt(pos) != ']') {
                            pos++;
                        }
                        return s.substring(start, pos).trim();
                    }
                }

                java.util.List<Object> parseArray() throws Exception {
                    java.util.List<Object> list = new java.util.ArrayList<>();
                    if (s.charAt(pos) != '[') {
                        throw new Exception("Expected '[' at position " + pos);
                    }
                    pos++; 
                    skipWhitespace();
                    
                    if (pos < s.length() && s.charAt(pos) == ']') {
                        pos++;
                        return list;
                    }
                    while (true) {
                        skipWhitespace();
                        Object value = parseValue();
                        list.add(value);
                        skipWhitespace();
                        if (pos >= s.length()) break;
                        if (s.charAt(pos) == ',') {
                            pos++;
                            continue;
                        } else if (s.charAt(pos) == ']') {
                            pos++;
                            break;
                        } else {
                            throw new Exception("Unexpected character: " + s.charAt(pos));
                        }
                    }
                    return list;
                }
            }

            Parser parser = new Parser(trimmed);
            Object parsed = parser.parseValue();

            
            java.util.function.Function<Object, java.util.List<Integer>> getShape = new java.util.function.Function<Object, java.util.List<Integer>>() {
                @Override
                public java.util.List<Integer> apply(Object obj) {
                    java.util.List<Integer> shape = new java.util.ArrayList<>();
                    if (obj instanceof java.util.List) {
                        java.util.List<?> list = (java.util.List<?>) obj;
                        shape.add(list.size());
                        if (list.size() > 0) {
                            Object first = list.get(0);
                            java.util.List<Integer> subshape = apply(first);
                            shape.addAll(subshape);
                        }
                    }
                    return shape;
                }
            };
            java.util.List<Integer> shapeList = getShape.apply(parsed);

            
            java.util.function.Predicate<Integer> isPowerOfTwo = (n) -> (n > 0) && ((n & (n - 1)) == 0);

            
            if (shapeList.size() == 1) {
                int n = shapeList.get(0);
                double[] data = new double[n];
                for (int i = 0; i < n; i++) {
                    data[i] = 0.0;
                }
                if(isPowerOfTwo.test(n)){
                    new DoubleFFT_1D(n).realForward(data);
                } else {
                    
                    new DoubleFFT_1D(n);
                }
            } else if (shapeList.size() == 2) {
                int rows = shapeList.get(0);
                int cols = shapeList.get(1);
                double[][] data = new double[rows][cols];
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        data[i][j] = 0.0;
                    }
                }
                if (isPowerOfTwo.test(rows) && isPowerOfTwo.test(cols)) {
                    new DoubleFFT_2D(rows, cols).realForward(data);
                } else {
                    new DoubleFFT_2D(rows, cols);
                }
            } else if (shapeList.size() == 3) {
                int dim1 = shapeList.get(0);
                int dim2 = shapeList.get(1);
                int dim3 = shapeList.get(2);
                double[][][] data = new double[dim1][dim2][dim3];
                for (int i = 0; i < dim1; i++) {
                    for (int j = 0; j < dim2; j++) {
                        for (int k = 0; k < dim3; k++) {
                            data[i][j][k] = 0.0;
                        }
                    }
                }
                if (isPowerOfTwo.test(dim1) && isPowerOfTwo.test(dim2) && isPowerOfTwo.test(dim3)) {
                    new DoubleFFT_3D(dim1, dim2, dim3).realForward(data);
                } else {
                    new DoubleFFT_3D(dim1, dim2, dim3);
                }
            } else {
                
            }

            
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (int i = 0; i < shapeList.size(); i++) {
                sb.append(shapeList.get(i));
                if (i < shapeList.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")");
            return sb.toString();
        } catch (Exception e) {
            return "Error in computing FFTN: " + e.getMessage();
        }
    }
}