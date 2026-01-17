#include <gtest/gtest.h>
#include "../src/function_shapely_union.cpp"

class TestGeometryUnion : public ::testing::Test {
protected:
    GeometryUnion geometry_union;
};

TEST_F(TestGeometryUnion, TestDisjointGeometries) {
    std::string geom1 = "POLYGON((0 0, 2 0, 1 2, 0 0))";
    std::string geom2 = "POLYGON((3 0, 5 0, 4 2, 3 0))";
    std::string result = geometry_union.calculate_union(geom1, geom2);
    EXPECT_NE(result.find("MULTIPOLYGON"), std::string::npos);
    EXPECT_NE(result.find("((0 0, 2 0, 1 2, 0 0))"), std::string::npos);
    EXPECT_NE(result.find("((3 0, 5 0, 4 2, 3 0))"), std::string::npos);
}

TEST_F(TestGeometryUnion, TestOverlappingGeometries) {
    std::string geom1 = "POLYGON((0 0, 2 0, 1 2, 0 0))";
    std::string geom2 = "POLYGON((1 0, 3 0, 2 2, 1 0))";
    std::string result = geometry_union.calculate_union(geom1, geom2);
    EXPECT_NE(result.find("POLYGON"), std::string::npos);
    EXPECT_NE(result.find("(0 0, 1 2, 1.5 1, 2 2, 3 0, 2 0, 1 0, 0 0)"), std::string::npos);
}

TEST_F(TestGeometryUnion, TestGeometryCollection) {
    std::string geom1 = "POINT(1 1)";
    std::string geom2 = "POLYGON((0 0, 2 0, 1 2, 0 0))";
    std::string result = geometry_union.calculate_union(geom1, geom2);
    EXPECT_TRUE(result.find("POLYGON ((1 2, 2 0, 0 0, 1 2))") != std::string::npos ||
                result.find("GEOMETRYCOLLECTION") != std::string::npos);
}

TEST_F(TestGeometryUnion, TestInvalidGeometry) {
    std::string geom1 = "INVALID GEOMETRY";
    std::string geom2 = "POLYGON((0 0, 2 0, 1 2, 0 0))";
    std::string expected = "Error: Could not create geometry because of errors while reading input.";
    EXPECT_EQ(geometry_union.calculate_union(geom1, geom2), expected);
}

TEST_F(TestGeometryUnion, TestEmptyGeometries) {
    std::string geom1 = "GEOMETRYCOLLECTION EMPTY";
    std::string geom2 = "GEOMETRYCOLLECTION EMPTY";
    std::string expected = "GEOMETRYCOLLECTION EMPTY";
    EXPECT_EQ(geometry_union.calculate_union(geom1, geom2), expected);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}