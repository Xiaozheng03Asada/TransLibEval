package com.example;

import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTWriter;

public class GeometryBuffer {
    public String calculate_buffer(String wkt, float distance) {
        class GeometryBuffer {
            public String process(String wkt, float distance) {
                try {
                    if (wkt.equals("GEOMETRYCOLLECTION EMPTY")) {
                        return "GEOMETRYCOLLECTION EMPTY";
                    }
                    WKTReader reader = new WKTReader();
                    Geometry geom = reader.read(wkt);
                    Geometry result = geom.buffer(distance);
                    WKTWriter writer = new WKTWriter();
                    String resultStr = writer.write(result);
                    // 如果结果是空的，返回GEOMETRYCOLLECTION EMPTY而不是POLYGON EMPTY
                    if (resultStr.endsWith("EMPTY")) {
                        return "GEOMETRYCOLLECTION EMPTY";
                    }
                    return resultStr;
                } catch (ParseException e) {
                    return "Error: Could not process the input geometry.";
                }
            }
        }
        return new GeometryBuffer().process(wkt, distance);
    }
}