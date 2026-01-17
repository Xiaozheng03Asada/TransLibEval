#include <gtest/gtest.h>
#include "../src/function_levenshtein_editops.cpp"

class EditopsTest : public ::testing::Test {
protected:
    StringProcessor processor;
};

TEST_F(EditopsTest, EqualStrings) {
    EXPECT_EQ(processor.calculate_editops(boost::any(std::string("test")), 
                                        boost::any(std::string("test"))), "[]");
}

TEST_F(EditopsTest, SingleSubstitution) {
    EXPECT_EQ(processor.calculate_editops(boost::any(std::string("flaw")), 
                                        boost::any(std::string("lawn"))), 
              "[('delete', 0, 0), ('insert', 4, 3)]");
}

TEST_F(EditopsTest, InsertionsAndDeletions) {
    EXPECT_EQ(processor.calculate_editops(boost::any(std::string("hello")), 
                                        boost::any(std::string("helo"))), 
              "[('delete', 3, 3)]");
    EXPECT_EQ(processor.calculate_editops(boost::any(std::string("helo")), 
                                        boost::any(std::string("hello"))), 
              "[('insert', 3, 3)]");
}

TEST_F(EditopsTest, MultipleOperations) {
    EXPECT_EQ(processor.calculate_editops(boost::any(std::string("kitten")), 
                                        boost::any(std::string("sitting"))), 
              "[('replace', 0, 0), ('replace', 4, 4), ('insert', 6, 6)]");
}

TEST_F(EditopsTest, NonStringInput) {
    EXPECT_EQ(processor.calculate_editops(boost::any(123), 
                                        boost::any(std::string("hello"))), 
              "Error: Both inputs must be strings");
    EXPECT_EQ(processor.calculate_editops(boost::any(std::string("hello")), 
                                        boost::any(nullptr)), 
              "Error: Both inputs must be strings");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}