#include <gtest/gtest.h>
#include "../src/function_shapely_intersects.cpp"

class TestGeometryIntersects : public ::testing::Test {
protected:
    GeometryIntersects geometry_intersects;
};

TEST_F(TestGeometryIntersects, TestIntersectingPolygons) {
    std::string geom1 = "POLYGON((0 0, 2 0, 1 2, 0 0))";
    std::string geom2 = "POLYGON((1 0, 3 0, 2 2, 1 0))";
    EXPECT_TRUE(geometry_intersects.check_intersects(geom1, geom2));
}

TEST_F(TestGeometryIntersects, TestNonIntersectingPolygons) {
    std::string geom1 = "POLYGON((0 0, 2 0, 1 2, 0 0))";
    std::string geom2 = "POLYGON((3 0, 5 0, 4 2, 3 0))";
    EXPECT_FALSE(geometry_intersects.check_intersects(geom1, geom2));
}

TEST_F(TestGeometryIntersects, TestIntersectingLineAndPolygon) {
    std::string geom1 = "LINESTRING(0 0, 2 2)";
    std::string geom2 = "POLYGON((1 1, 3 1, 2 3, 1 1))";
    EXPECT_TRUE(geometry_intersects.check_intersects(geom1, geom2));
}

TEST_F(TestGeometryIntersects, TestDisjointLineAndPolygon) {
    std::string geom1 = "LINESTRING(0 0, 1 1)";
    std::string geom2 = "POLYGON((2 2, 4 2, 3 4, 2 2))";
    EXPECT_FALSE(geometry_intersects.check_intersects(geom1, geom2));
}

TEST_F(TestGeometryIntersects, TestInvalidGeometry) {
    std::string geom1 = "INVALID GEOMETRY";
    std::string geom2 = "POLYGON((0 0, 2 0, 1 2, 0 0))";
    EXPECT_FALSE(geometry_intersects.check_intersects(geom1, geom2));
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}