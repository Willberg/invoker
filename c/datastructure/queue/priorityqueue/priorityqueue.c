//
// Created by john on 2022/9/30.
//
#include "priorityqueue.h"

bool isEmpty(PriorityQueue *pq) {
    return pq->size == 0;
}

bool isFull(PriorityQueue *pq) {
    return pq->size == pq->capacity;
}

void *top(PriorityQueue *pq) {
    if (isEmpty(pq)) {
        return NULL;
    }
    return pq->q[0];
}

void swap(PriorityQueue *pq, int i, int j) {
    void *t = pq->q[i];
    pq->q[i] = pq->q[j];
    pq->q[j] = t;
}

bool less(PriorityQueue *pq, int i, int j) {
    return pq->less(pq->q[i], pq->q[j]);
}

void up(PriorityQueue *pq, int p) {
    while (p > 0) {
        int r = (p - 1) / 2;
        if (less(pq, r, p)) {
            return;
        }
        swap(pq, r, p);
        p = r;
    }
}

void down(PriorityQueue *pq, int p) {
    while (p < pq->size) {
        int small = p, l = 2 * p + 1;
        if (l < pq->size && less(pq, l, small)) {
            small = l;
        }
        int r = l + 1;
        if (r < pq->size && less(pq, r, small)) {
            small = r;
        }
        if (small == p) {
            return;
        }
        swap(pq, small, p);
        p = small;
    }
}

bool push(PriorityQueue *pq, void *t) {
    if (isFull(pq)) {
        return false;
    }
    pq->q[pq->size] = t;
    pq->size++;
    up(pq, pq->size - 1);
    return true;
}

void *pop(PriorityQueue *pq) {
    if (isEmpty(pq)) {
        return NULL;
    }
    void *top = pq->q[0];
    pq->size--;
    pq->q[0] = pq->q[pq->size];
    down(pq, 0);
    return top;
}


PriorityQueue createPriorityQueue(int n, bool (*less)(void *, void *)) {
    PriorityQueue pq;
    pq.size = 0;
    pq.capacity = n;
    pq.q = (void **) malloc(n * sizeof(void *));
    pq.less = less;
    return pq;
}

void releasePriorityQueue(PriorityQueue *pq) {
    if (pq) {
        free(pq->q);
        pq->q = NULL;
    }
}