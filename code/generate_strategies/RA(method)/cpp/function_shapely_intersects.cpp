#include <geos_c.h>
#include <string>

class GeometryIntersects {
public:
    bool check_intersects(const std::string& wkt1, const std::string& wkt2) {
        // 初始化 GEOS
        initGEOS(nullptr, nullptr);

        // 创建 WKT 读取器
        GEOSWKTReader* reader = GEOSWKTReader_create();
        if (!reader) {
            finishGEOS();
            return false;
        }

        // 解析 WKT 字符串
        GEOSGeometry* geom1 = GEOSWKTReader_read(reader, wkt1.c_str());
        GEOSGeometry* geom2 = GEOSWKTReader_read(reader, wkt2.c_str());
        
        // 检查几何图形是否相交
        bool result = false;
        if (geom1 && geom2) {
            result = GEOSIntersects(geom1, geom2) == 1;
        }

        // 清理资源
        if (geom1) GEOSGeom_destroy(geom1);
        if (geom2) GEOSGeom_destroy(geom2);
        GEOSWKTReader_destroy(reader);
        finishGEOS();

        return result;
    }
};