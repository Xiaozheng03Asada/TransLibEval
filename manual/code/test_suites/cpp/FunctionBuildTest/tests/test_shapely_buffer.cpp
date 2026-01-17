#include <gtest/gtest.h>
#include "../src/function_shapely_buffer.cpp"

class TestGeometryBuffer : public ::testing::Test {
protected:
    void SetUp() override {
        geometry_buffer = new GeometryBuffer();
    }

    void TearDown() override {
        delete geometry_buffer;
    }

    GeometryBuffer* geometry_buffer;
};

TEST_F(TestGeometryBuffer, TestPointBuffer) {
    std::string geom = "POINT(0 0)";
    double distance = 1.0;
    std::string expected_start = "POLYGON";
    std::string result = geometry_buffer->calculate_buffer(geom, distance);
    EXPECT_EQ(result.substr(0, expected_start.length()), expected_start);
}

TEST_F(TestGeometryBuffer, TestPolygonBuffer) {
    std::string geom = "POLYGON((0 0, 2 0, 1 2, 0 0))";
    double distance = 0.5;
    std::string expected_start = "POLYGON";
    std::string result = geometry_buffer->calculate_buffer(geom, distance);
    EXPECT_EQ(result.substr(0, expected_start.length()), expected_start);
}

TEST_F(TestGeometryBuffer, TestNegativeBuffer) {
    std::string geom = "POLYGON((0 0, 4 0, 2 4, 0 0))";
    double distance = -0.5;
    std::string expected_start = "POLYGON";
    std::string result = geometry_buffer->calculate_buffer(geom, distance);
    EXPECT_EQ(result.substr(0, expected_start.length()), expected_start);
}

TEST_F(TestGeometryBuffer, TestEmptyGeometry) {
    std::string geom = "GEOMETRYCOLLECTION EMPTY";
    double distance = 1.0;
    std::string expected = "GEOMETRYCOLLECTION EMPTY";
    EXPECT_EQ(geometry_buffer->calculate_buffer(geom, distance), expected);
}

TEST_F(TestGeometryBuffer, TestInvalidGeometry) {
    std::string geom = "INVALID GEOMETRY";
    double distance = 1.0;
    std::string expected = "Error: Could not process the input geometry.";
    EXPECT_EQ(geometry_buffer->calculate_buffer(geom, distance), expected);
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}