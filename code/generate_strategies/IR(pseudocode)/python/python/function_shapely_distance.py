from shapely.wkt import loads

class GeometryDistance:
    def calculate_distance(self, geom1: str, geom2: str) -> float:
        try:
            shape1 = loads(geom1)
            shape2 = loads(geom2)

            return shape1.distance(shape2)
        except Exception:
            return -1.0  
