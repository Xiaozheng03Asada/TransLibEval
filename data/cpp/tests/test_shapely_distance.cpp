#include <gtest/gtest.h>
#include "../src/function_shapely_distance.cpp"

class TestGeometryDistance : public ::testing::Test {
protected:
    void SetUp() override {
        geometry_distance = new GeometryDistance();
    }

    void TearDown() override {
        delete geometry_distance;
    }

    GeometryDistance* geometry_distance;
};

TEST_F(TestGeometryDistance, TestPointToPoint) {
    std::string geom1 = "POINT(0 0)";
    std::string geom2 = "POINT(3 4)";
    double expected = 5.0;
    EXPECT_EQ(geometry_distance->calculate_distance(geom1, geom2), expected);
}

TEST_F(TestGeometryDistance, TestPointToLine) {
    std::string geom1 = "POINT(1 1)";
    std::string geom2 = "LINESTRING(0 0, 0 2)";
    double expected = 1.0;
    EXPECT_EQ(geometry_distance->calculate_distance(geom1, geom2), expected);
}

TEST_F(TestGeometryDistance, TestPointToPolygon) {
    std::string geom1 = "POINT(5 5)";
    std::string geom2 = "POLYGON((0 0, 4 0, 4 4, 0 4, 0 0))";
    double expected = 1.4142135623730951;
    EXPECT_EQ(geometry_distance->calculate_distance(geom1, geom2), expected);
}

TEST_F(TestGeometryDistance, TestIntersectingObjects) {
    std::string geom1 = "LINESTRING(0 0, 2 2)";
    std::string geom2 = "POLYGON((1 1, 3 1, 3 3, 1 3, 1 1))";
    double expected = 0.0;
    EXPECT_EQ(geometry_distance->calculate_distance(geom1, geom2), expected);
}

TEST_F(TestGeometryDistance, TestInvalidGeometry) {
    std::string geom1 = "INVALID GEOMETRY";
    std::string geom2 = "POINT(0 0)";
    double expected = -1.0;
    EXPECT_EQ(geometry_distance->calculate_distance(geom1, geom2), expected);
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}