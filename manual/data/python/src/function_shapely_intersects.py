from shapely.wkt import loads

class GeometryIntersects:
    def check_intersects(self, wkt1: str, wkt2: str) -> bool:
        
        try:
            geom1 = loads(wkt1)
            geom2 = loads(wkt2)
            return geom1.intersects(geom2)
        except Exception:
            return False
