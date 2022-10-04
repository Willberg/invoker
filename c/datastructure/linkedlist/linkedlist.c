//
// Created by john on 2022/9/23.
//
#include "linkedlist.h"

ListNode *listNodeCreate(int val) {
    ListNode *node = (ListNode *) malloc(sizeof(ListNode));
    node->val = val;
    node->next = NULL;
    return node;
}

ListNode *find(LinkedList *list, int idx) {
    if (idx < 0 || idx >= list->size) {
        return NULL;
    }
    ListNode *p = list->head;
    for (int i = 0; i <= idx; i++) {
        p = p->next;
    }
    return p;
}

int linkedListGet(LinkedList *obj, int index) {
    ListNode *p = find(obj, index);
    if (!p) {
        return -1;
    }
    return p->val;
}


void linkedListAddAtIndex(LinkedList *obj, int index, int val) {
    if (index > obj->size) {
        return;
    }
    ListNode *p = obj->head;
    if (index > 0) {
        p = find(obj, index - 1);
    }
    ListNode *t = listNodeCreate(val);
    t->next = p->next;
    p->next = t;
    obj->size++;
}

void linkedListAddAtHead(LinkedList *obj, int val) {
    linkedListAddAtIndex(obj, 0, val);
}

void linkedListAddAtTail(LinkedList *obj, int val) {
    linkedListAddAtIndex(obj, obj->size, val);
}

void linkedListDeleteAtIndex(LinkedList *obj, int index) {
    if (obj->size == 0) {
        return;
    }
    ListNode *p = obj->head;
    if (index > 0) {
        p = find(obj, index - 1);
    }
    ListNode *tmp = p->next;
    p->next = p->next->next;
    free(tmp);
}

LinkedList *linkedListCreate() {
    LinkedList *list = (LinkedList *) malloc(sizeof(LinkedList));
    list->size = 0;
    list->head = listNodeCreate(-1);
    return list;
}

void linkedListFree(LinkedList *obj) {
    ListNode *cur = obj->head, *tmp = NULL;
    while (cur) {
        tmp = cur;
        cur = cur->next;
        free(tmp);
    }
    free(obj);
}