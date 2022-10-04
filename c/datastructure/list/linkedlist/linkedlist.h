//
// Created by john on 2022/10/1.
//

#ifndef TEST_LINKEDLIST_H
#define TEST_LINKEDLIST_H

#include <stdlib.h>

typedef struct ListNode {
    int val;
    struct ListNode *next;
} ListNode;

typedef struct {
    int size;
    ListNode *head;
} LinkedList;

LinkedList *linkedListCreate();

void linkedListFree(LinkedList *);

ListNode *find(LinkedList *, int);

int linkedListGet(LinkedList *, int);

void linkedListAddAtHead(LinkedList *, int);

void linkedListAddAtTail(LinkedList *, int);

void linkedListAddAtIndex(LinkedList *, int, int);

void linkedListDeleteAtIndex(LinkedList *, int);

#endif //TEST_LINKEDLIST_H
