#include <geos_c.h> 
#include <string>
#include <sstream>
#include <stdexcept>

class GeometryBuffer {
public:
    std::string calculate_buffer(const std::string& wkt, double distance) {
        // 初始化GEOS
        initGEOS(nullptr, nullptr);

        // 创建读写器
        GEOSWKTReader* reader = GEOSWKTReader_create();
        GEOSWKTWriter* writer = GEOSWKTWriter_create();
        
        // 解析WKT
        GEOSGeometry* geom = GEOSWKTReader_read(reader, wkt.c_str());
        if (!geom) {
            GEOSWKTReader_destroy(reader);
            GEOSWKTWriter_destroy(writer);
            finishGEOS();
            return "Error: Could not process the input geometry.";
        }

        // 检查空几何
        if (GEOSisEmpty(geom)) {
            GEOSWKTReader_destroy(reader);
            GEOSWKTWriter_destroy(writer);
            GEOSGeom_destroy(geom);
            finishGEOS();
            return "GEOMETRYCOLLECTION EMPTY";
        }

        // 计算缓冲区
        GEOSGeometry* result = GEOSBuffer(geom, distance, 8);
        if (!result) {
            GEOSWKTReader_destroy(reader);
            GEOSWKTWriter_destroy(writer);
            GEOSGeom_destroy(geom);
            finishGEOS();
            return "Error: Could not process the input geometry.";
        }

        // 转换结果为WKT
        char* result_wkt = GEOSWKTWriter_write(writer, result);
        std::string result_str(result_wkt);

        // 清理资源
        GEOSFree(result_wkt);
        GEOSGeom_destroy(geom);
        GEOSGeom_destroy(result);
        GEOSWKTReader_destroy(reader);
        GEOSWKTWriter_destroy(writer);
        finishGEOS();

        return result_str;
    }
};