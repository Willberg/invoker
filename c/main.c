//
// Created by john on 2022/10/4.
//
#include <stdio.h>

int minAddToMakeValid(char *);

void test_greedy_min_add() {
    printf("%d\n", minAddToMakeValid("))("));
}

int main() {
    test_greedy_min_add();
}