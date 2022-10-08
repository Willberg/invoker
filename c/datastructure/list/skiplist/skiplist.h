//
// Created by john on 2022/10/7.
//

#ifndef C_SKIPLIST_H
#define C_SKIPLIST_H

#include <stdlib.h>
#include <time.h>

#define max(a, b) ((a) > (b)? (a) : (b))
#define bool int
#define true 1
#define false 0

typedef struct Node {
    int val;
    int size;
    struct Node **forward;
} Node;

typedef struct {
    int level;
    Node *head;
} Skiplist;

Skiplist *skiplistCreate();

bool skiplistSearch(Skiplist *, int);

void skiplistAdd(Skiplist *, int);

bool skiplistErase(Skiplist *, int);

void skiplistFree(Skiplist *);

#endif //C_SKIPLIST_H
