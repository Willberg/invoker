//
// Created by john on 2022/10/1.
//

#ifndef TEST_PRIORITYQUEUE_H
#define TEST_PRIORITYQUEUE_H

#include <malloc.h>

#define true 1
#define false 0

typedef int bool;

typedef struct {
    int size;
    int capacity;
    void **q;

    bool (*less)(void *, void *);
} PriorityQueue;

PriorityQueue createPriorityQueue(int, bool (*less)(void *, void *));

void releasePriorityQueue(PriorityQueue *);

bool isEmpty(PriorityQueue *);

bool isFull(PriorityQueue *);

void *top(PriorityQueue *);

bool push(PriorityQueue *, void *);

void *pop(PriorityQueue *);

#endif //TEST_PRIORITYQUEUE_H
