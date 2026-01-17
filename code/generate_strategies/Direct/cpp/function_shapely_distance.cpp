#include <geos_c.h>
#include <string>
#include <stdexcept>

class GeometryDistance {
public:
    double calculate_distance(const std::string& geom1, const std::string& geom2) {
        // 初始化GEOS上下文
        GEOSContextHandle_t context = GEOS_init_r();
        if (!context) {
            return -1.0;
        }

        try {
            // 创建WKT读取器
            GEOSWKTReader* reader = GEOSWKTReader_create_r(context);
            if (!reader) {
                GEOS_finish_r(context);
                return -1.0;
            }

            // 解析几何体
            GEOSGeometry* g1 = GEOSWKTReader_read_r(context, reader, geom1.c_str());
            GEOSGeometry* g2 = GEOSWKTReader_read_r(context, reader, geom2.c_str());
            GEOSWKTReader_destroy_r(context, reader);

            if (!g1 || !g2) {
                if (g1) GEOSGeom_destroy_r(context, g1);
                if (g2) GEOSGeom_destroy_r(context, g2);
                GEOS_finish_r(context);
                return -1.0;
            }

            // 计算距离
            double distance = 0.0;
            int success = GEOSDistance_r(context, g1, g2, &distance);
            
            // 清理几何体
            GEOSGeom_destroy_r(context, g1);
            GEOSGeom_destroy_r(context, g2);

            // 清理GEOS上下文
            GEOS_finish_r(context);

            if (success != 1) {
                return -1.0;
            }

            return distance;
        } catch (const std::exception&) {
            GEOS_finish_r(context);
            return -1.0;
        }
    }
};