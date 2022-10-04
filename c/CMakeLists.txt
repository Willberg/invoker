cmake_minimum_required(VERSION 3.17)
project(c C)

set(CMAKE_C_STANDARD 11)

add_executable(c
        algorithm/greedy/minadd/minadd.c
        main.c)
