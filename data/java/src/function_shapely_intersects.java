package com.example;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class GeometryIntersects {
    public boolean check_intersects(String wkt1, String wkt2) {
        class GeometryIntersects {
            boolean checkIntersection(String wkt1, String wkt2) {
                try {
                    WKTReader reader = new WKTReader();
                    Geometry geom1 = reader.read(wkt1);
                    Geometry geom2 = reader.read(wkt2);
                    return geom1.intersects(geom2);
                } catch (ParseException e) {
                    return false;
                }
            }
        }
        return new GeometryIntersects().checkIntersection(wkt1, wkt2);
    }
}