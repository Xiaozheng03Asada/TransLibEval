#include <geos_c.h>
#include <string>
#include <stdexcept>
#include <sstream>
#include <iomanip>

class GeometryUnion {
public:
    std::string calculate_union(const std::string& wkt1, const std::string& wkt2) {
        // 初始化GEOS
        initGEOS(nullptr, nullptr);
        
        GEOSWKTReader* reader = GEOSWKTReader_create();
        GEOSWKTWriter* writer = GEOSWKTWriter_create();
        
        // 设置输出格式，提高精度
        GEOSWKTWriter_setRoundingPrecision(writer, 1);  // 设置为1位小数
        GEOSWKTWriter_setTrim(writer, 1);  // 移除尾随零
        
        GEOSGeometry* geom1 = nullptr;
        GEOSGeometry* geom2 = nullptr;
        
        try {
            geom1 = GEOSWKTReader_read(reader, wkt1.c_str());
            geom2 = GEOSWKTReader_read(reader, wkt2.c_str());
            
            if (!geom1 || !geom2) {
                throw std::runtime_error("Invalid geometry");
            }
            
            if (GEOSisEmpty(geom1) && GEOSisEmpty(geom2)) {
                GEOSWKTReader_destroy(reader);
                GEOSWKTWriter_destroy(writer);
                finishGEOS();
                return "GEOMETRYCOLLECTION EMPTY";
            }
            
            GEOSGeometry* result = GEOSUnion(geom1, geom2);
            if (!result) {
                throw std::runtime_error("Union operation failed");
            }
            
            char* wkt_result = GEOSWKTWriter_write(writer, result);
            std::string result_str(wkt_result);
            
            // 清理资源
            GEOSFree(wkt_result);
            GEOSGeom_destroy(result);
            GEOSGeom_destroy(geom1);
            GEOSGeom_destroy(geom2);
            GEOSWKTReader_destroy(reader);
            GEOSWKTWriter_destroy(writer);
            finishGEOS();
            
            return result_str;
            
        } catch (...) {
            if (geom1) GEOSGeom_destroy(geom1);
            if (geom2) GEOSGeom_destroy(geom2);
            GEOSWKTReader_destroy(reader);
            GEOSWKTWriter_destroy(writer);
            finishGEOS();
            return "Error: Could not create geometry because of errors while reading input.";
        }
    }
};
