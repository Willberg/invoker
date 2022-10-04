cmake_minimum_required(VERSION 3.17)
project(c C)

set(CMAKE_C_STANDARD 11)

add_executable(c
        algorithm/greedy/minadd/minadd.c
        algorithm/greedy/test/test_min_add.c
        datastructure/list/linkedlist/linkedlist.c
        datastructure/queue/priorityqueue/priorityqueue.c
        datastructure/queue/test/mergeklist/test_mergeklist.c
        datastructure/list/test/linkedlist/test_linkedlist.c
        main.c)
