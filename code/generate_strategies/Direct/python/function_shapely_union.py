from shapely.wkt import loads

class GeometryUnion:
    def calculate_union(self, wkt1: str, wkt2: str) -> str:
        
        try:
            geom1 = loads(wkt1)
            geom2 = loads(wkt2)
            result = geom1.union(geom2)
            return result.wkt if result else "GEOMETRYCOLLECTION EMPTY"
        except Exception as e:
            return "Error: Could not create geometry because of errors while reading input."
