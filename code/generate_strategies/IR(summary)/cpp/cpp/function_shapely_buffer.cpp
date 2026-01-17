#include <geos_c.h> 
#include <string>
#include <sstream>
#include <stdexcept>

class GeometryBuffer {
public:
    std::string calculate_buffer(const std::string& wkt, double distance) {
        
        initGEOS(nullptr, nullptr);

        
        GEOSWKTReader* reader = GEOSWKTReader_create();
        GEOSWKTWriter* writer = GEOSWKTWriter_create();
        
        
        GEOSGeometry* geom = GEOSWKTReader_read(reader, wkt.c_str());
        if (!geom) {
            GEOSWKTReader_destroy(reader);
            GEOSWKTWriter_destroy(writer);
            finishGEOS();
            return "Error: Could not process the input geometry.";
        }

        
        if (GEOSisEmpty(geom)) {
            GEOSWKTReader_destroy(reader);
            GEOSWKTWriter_destroy(writer);
            GEOSGeom_destroy(geom);
            finishGEOS();
            return "GEOMETRYCOLLECTION EMPTY";
        }

        
        GEOSGeometry* result = GEOSBuffer(geom, distance, 8);
        if (!result) {
            GEOSWKTReader_destroy(reader);
            GEOSWKTWriter_destroy(writer);
            GEOSGeom_destroy(geom);
            finishGEOS();
            return "Error: Could not process the input geometry.";
        }

        
        char* result_wkt = GEOSWKTWriter_write(writer, result);
        std::string result_str(result_wkt);

        
        GEOSFree(result_wkt);
        GEOSGeom_destroy(geom);
        GEOSGeom_destroy(result);
        GEOSWKTReader_destroy(reader);
        GEOSWKTWriter_destroy(writer);
        finishGEOS();

        return result_str;
    }
};