#include <gtest/gtest.h>
#include "../src/function_sklearn_StandardScaler.cpp"


class TestStandardScalerTestCase : public ::testing::Test {
protected:
    StandardScalerFunction scaler;
};

TEST_F(TestStandardScalerTestCase, TestShapeConsistency) {
    std::string data = "1.0,-1.0,2.0;2.0,0.0,0.0;0.0,1.0,-1.0;3.0,2.0,1.0";
    std::string result = scaler.quick_sort_from_string(data);
    EXPECT_EQ(typeid(result).name(), typeid(std::string).name());
}

TEST_F(TestStandardScalerTestCase, TestMeanValueNearZero) {
    std::string data = "1.0,-1.0,2.0;2.0,0.0,0.0;0.0,1.0,-1.0;3.0,2.0,1.0";
    std::string result = scaler.quick_sort_from_string(data);
    std::istringstream ss(result);
    std::string line;
    std::vector<std::vector<double>> result_data;
    while (std::getline(ss, line, ';')) {
        std::vector<double> row;
        std::istringstream line_ss(line);
        std::string item;
        while (std::getline(line_ss, item, ',')) {
            row.push_back(std::stod(item));
        }
        result_data.push_back(row);
    }
    Eigen::MatrixXd result_matrix(result_data.size(), result_data[0].size());
    for (size_t i = 0; i < result_data.size(); ++i) {
        for (size_t j = 0; j < result_data[i].size(); ++j) {
            result_matrix(i, j) = result_data[i][j];
        }
    }
    Eigen::VectorXd mean = result_matrix.colwise().mean();
    EXPECT_TRUE(mean.isApprox(Eigen::VectorXd::Zero(mean.size()), 0.1));
}

TEST_F(TestStandardScalerTestCase, TestStdValueNearOne) {
    std::string data = "1.0,-1.0,2.0;2.0,0.0,0.0;0.0,1.0,-1.0;3.0,2.0,1.0";
    std::string result = scaler.quick_sort_from_string(data);
    std::istringstream ss(result);
    std::string line;
    std::vector<std::vector<double>> result_data;
    while (std::getline(ss, line, ';')) {
        std::vector<double> row;
        std::istringstream line_ss(line);
        std::string item;
        while (std::getline(line_ss, item, ',')) {
            row.push_back(std::stod(item));
        }
        result_data.push_back(row);
    }
    Eigen::MatrixXd result_matrix(result_data.size(), result_data[0].size());
    for (size_t i = 0; i < result_data.size(); ++i) {
        for (size_t j = 0; j < result_data[i].size(); ++j) {
            result_matrix(i, j) = result_data[i][j];
        }
    }
    Eigen::VectorXd std_dev = ((result_matrix.array().square().colwise().sum()) / (result_matrix.rows() - 1)).sqrt();
    EXPECT_TRUE(std_dev.isApprox(Eigen::VectorXd::Ones(std_dev.size()), 0.1));
}

TEST_F(TestStandardScalerTestCase, TestInvalidDataType) {
    std::string data = "a,2;3,b;5,6";
    EXPECT_THROW(scaler.quick_sort_from_string(data), std::invalid_argument);
}

TEST_F(TestStandardScalerTestCase, Test2DArrayData) {
    std::string data = "[[[1, 2], [3, 4]], [[5, 6], [7, 8]]]";
    EXPECT_THROW(scaler.quick_sort_from_string(data), std::invalid_argument);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}