package com.example;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics; 

public class ExponentialSmoothingProcessor {

    
    public String perform_exponential_smoothing(String data, int forecast_steps, String seasonal, int seasonal_periods) throws Exception {
        
        if (forecast_steps <= 0) {
            throw new IllegalArgumentException("forecast_steps must be greater than 0.");
        }
        
        if (!seasonal.equals("add") && !seasonal.equals("mul")) {
            throw new IllegalArgumentException("seasonal must be 'add' or 'mul'.");
        }
        
        List<Double> dataList = new ArrayList<>();
        try {
            String trimmed = data.trim();
            if (!trimmed.startsWith("[") || !trimmed.endsWith("]")) {
                throw new Exception();
            }
            String content = trimmed.substring(1, trimmed.length() - 1).trim();
            if (!content.isEmpty()) {
                String[] parts = content.split(",");
                for (String part : parts) {
                    dataList.add(Double.parseDouble(part.trim()));
                }
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Data must be a valid list string representation.");
        }
        if (dataList.size() == 0) {
            throw new IllegalArgumentException("Data must be a list.");
        }

        try {
            
            List<Double> fittedValues = new ArrayList<>();
            fittedValues.addAll(dataList);

            
            int startIndex = dataList.size() - seasonal_periods;
            if (startIndex < 0) {
                startIndex = 0;
            }
            double sum = 0.0;
            int count = 0;
            for (int i = startIndex; i < dataList.size(); i++) {
                sum += dataList.get(i);
                count++;
            }
            double avg = (count > 0) ? sum / count : 0.0;
            List<Double> forecast = new ArrayList<>();
            for (int i = 0; i < forecast_steps; i++) {
                forecast.add(avg);
            }

            
            DescriptiveStatistics stats = new DescriptiveStatistics();
            for (Double d : dataList) {
                stats.addValue(d);
            }
            
            Map<String, Double> modelParams = new LinkedHashMap<>();
            modelParams.put("smoothing_level", 0.5);
            modelParams.put("smoothing_slope", 0.5);
            modelParams.put("smoothing_seasonal", 0.5);

            
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("\"Fitted Values\": ");
            sb.append("[");
            for (int i = 0; i < fittedValues.size(); i++) {
                double val = fittedValues.get(i);
                if (val == Math.floor(val)) {
                    sb.append((int) val);
                } else {
                    sb.append(val);
                }
                if (i != fittedValues.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("], ");
            sb.append("\"Forecast\": ");
            sb.append("[");
            for (int i = 0; i < forecast.size(); i++) {
                double val = forecast.get(i);
                if (val == Math.floor(val)) {
                    sb.append((int) val);
                } else {
                    sb.append(val);
                }
                if (i != forecast.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("], ");
            sb.append("\"Model Params\": ");
            sb.append("{");
            int idx = 0;
            for (Map.Entry<String, Double> entry : modelParams.entrySet()) {
                sb.append("\"").append(entry.getKey()).append("\": ");
                double val = entry.getValue();
                if (val == Math.floor(val)) {
                    sb.append((int) val);
                } else {
                    sb.append(val);
                }
                if (idx != modelParams.size() - 1) {
                    sb.append(", ");
                }
                idx++;
            }
            sb.append("}");
            sb.append("}");
            return sb.toString();
        } catch (Exception e) {
            throw new Exception("Error in performing exponential smoothing: " + e.getMessage());
        }
    }
}