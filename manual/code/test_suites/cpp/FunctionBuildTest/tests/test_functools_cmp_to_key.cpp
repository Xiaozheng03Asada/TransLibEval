#include <gtest/gtest.h>
#include "../src/function_functools_cmp_to_key.cpp"

class TestNumberDictManager : public testing::Test {
protected:
    NumberDictManager manager;
};

TEST_F(TestNumberDictManager, AddOperation) {
    std::string result = manager.manage_number_dict("add,1,10;add,2,20;");
    EXPECT_EQ(result, "");
}

TEST_F(TestNumberDictManager, RemoveOperation) {
    std::string result = manager.manage_number_dict("add,1,10;remove,1;get,1;");
    EXPECT_EQ(result, "default");
}

TEST_F(TestNumberDictManager, GetOperation) {
    std::string result = manager.manage_number_dict("add,3,30;get,3;");
    EXPECT_EQ(result, "30");
}

TEST_F(TestNumberDictManager, SortOperation) {
    std::string result = manager.manage_number_dict("add,3,30;add,1,10;add,2,20;sort;");
    EXPECT_EQ(result, "1:10 2:20 3:30");
}

TEST_F(TestNumberDictManager, SumOperation) {
    std::string result = manager.manage_number_dict("add,1,10;add,2,20;sum;");
    EXPECT_EQ(result, "30");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}