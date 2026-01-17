# 设置 CMake 工具链文件和 Prefix 路径
set(CMAKE_PREFIX_PATH "/opt/homebrew/opt/libomp" CACHE STRING "OpenMP Prefix Path")
set(CMAKE_TOOLCHAIN_FILE "/Users/asada/vcpkg/scripts/buildsystems/vcpkg.cmake" CACHE STRING "Vcpkg Toolchain File")

cmake_minimum_required(VERSION 3.15)
project(test)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED True)

find_package(OpenMP REQUIRED)

find_package(Boost REQUIRED)
include_directories(${Boost_INCLUDE_DIRS})
find_package(date CONFIG REQUIRED)
find_package(GTest REQUIRED)
include_directories(${GTEST_INCLUDE_DIRS})

find_package(RapidJSON REQUIRED)
include_directories(${RapidJSON_INCLUDE_DIRS})

find_package(Eigen3 REQUIRED)
find_package(range-v3 REQUIRED)
find_package(nlohmann_json REQUIRED)
include_directories(${EIGEN3_INCLUDE_DIRS})
find_package(fmt REQUIRED)
find_package(xtensor REQUIRED)

# 查找所有的测试文件
file(GLOB_RECURSE test_files ${CMAKE_CURRENT_SOURCE_DIR}/tests/*.cpp)

# 为每个测试文件创建单独的可执行文件
foreach(test_file ${test_files})
    # 获取测试文件的名称（去掉路径）
    get_filename_component(test_name ${test_file} NAME_WE)

    # 创建每个测试文件的可执行文件
    add_executable(${test_name} ${test_file})

    # 链接所需的库
    target_link_libraries(${test_name}
            Boost::boost
            xtensor
            fmt::fmt
            nlohmann_json::nlohmann_json
            ${Boost_LIBRARIES}
            GTest::GTest
            Eigen3::Eigen
            date::date
            OpenMP::OpenMP_CXX
    )

    # 为所有测试文件链接 GTest::Main 来提供 main 函数
    target_link_libraries(${test_name} GTest::Main)

endforeach()
