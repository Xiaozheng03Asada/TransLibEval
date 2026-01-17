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

# 添加所有源文件
file(GLOB SRC_FILES "src/*.cpp")
add_executable(my_project ${SRC_FILES})
