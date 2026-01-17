from shapely.wkt import loads
from shapely.geometry import Point

class GeometryBuffer:
    def calculate_buffer(self, wkt: str, distance: float) -> str:
        
        try:
            geom = loads(wkt)
            result = geom.buffer(distance)
            return result.wkt if result else "GEOMETRYCOLLECTION EMPTY"
        except Exception:
            return "Error: Could not process the input geometry."
