cmake_minimum_required(VERSION 3.17)
project(cpp)

set(CMAKE_CXX_STANDARD 14)

add_executable(cpp main.cpp datastructure/list/skiplist/skiplist.h datastructure/list/test/skiplist/test_skiplist.cpp)