package com.example;

import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.geom.Geometry;

public class GeometryDistance {
    public double solution(String geom1, String geom2) {
        class GeometryDistance {
            public double calculate_distance(String geom1, String geom2) {
                try {
                    WKTReader reader = new WKTReader();
                    Geometry shape1 = reader.read(geom1);
                    Geometry shape2 = reader.read(geom2);

                    return shape1.distance(shape2);
                } catch (ParseException e) {
                    return -1.0;
                }
            }
        }

        GeometryDistance gd = new GeometryDistance();
        return gd.calculate_distance(geom1, geom2);
    }
}