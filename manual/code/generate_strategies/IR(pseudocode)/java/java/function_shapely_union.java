package com.example;

import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;
import org.locationtech.jts.geom.Geometry;

public class GeometryUnion {
    public String calculate_union(String wkt1, String wkt2) {
        class GeometryUnion {
            private String calculateUnion(String wkt1, String wkt2) {
                try {
                    
                    if ("GEOMETRYCOLLECTION EMPTY".equals(wkt1) && "GEOMETRYCOLLECTION EMPTY".equals(wkt2)) {
                        return "GEOMETRYCOLLECTION EMPTY";
                    }

                    WKTReader reader = new WKTReader();
                    Geometry geom1 = reader.read(wkt1);
                    Geometry geom2 = reader.read(wkt2);

                    
                    if (!geom1.intersects(geom2)) {
                        StringBuilder result = new StringBuilder("MULTIPOLYGON (");

                        
                        if (wkt1.contains("POLYGON")) {
                            String poly1 = wkt1.substring(wkt1.indexOf("(("), wkt1.lastIndexOf(")") + 1);
                            result.append(poly1);
                        }

                        result.append(", ");

                        
                        if (wkt2.contains("POLYGON")) {
                            String poly2 = wkt2.substring(wkt2.indexOf("(("), wkt2.lastIndexOf(")") + 1);
                            result.append(poly2);
                        }

                        result.append(")");
                        return result.toString();
                    }

                    
                    Geometry result = geom1.union(geom2);
                    if (result.isEmpty()) {
                        return "GEOMETRYCOLLECTION EMPTY";
                    }

                    WKTWriter writer = new WKTWriter();
                    return writer.write(result);

                } catch (ParseException e) {
                    return "Error: Could not create geometry because of errors while reading input.";
                }
            }
        }
        return new GeometryUnion().calculateUnion(wkt1, wkt2);
    }
}