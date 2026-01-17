#include <geos_c.h>
#include <string>

class GeometryIntersects {
public:
    bool check_intersects(const std::string& wkt1, const std::string& wkt2) {
        
        initGEOS(nullptr, nullptr);

        
        GEOSWKTReader* reader = GEOSWKTReader_create();
        if (!reader) {
            finishGEOS();
            return false;
        }

        
        GEOSGeometry* geom1 = GEOSWKTReader_read(reader, wkt1.c_str());
        GEOSGeometry* geom2 = GEOSWKTReader_read(reader, wkt2.c_str());
        
        
        bool result = false;
        if (geom1 && geom2) {
            result = GEOSIntersects(geom1, geom2) == 1;
        }

        
        if (geom1) GEOSGeom_destroy(geom1);
        if (geom2) GEOSGeom_destroy(geom2);
        GEOSWKTReader_destroy(reader);
        finishGEOS();

        return result;
    }
};