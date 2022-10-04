//
// Created by john on 2022/10/4.
//
#include "../../../list/linkedlist/linkedlist.h"

LinkedList *createList(int *a, int n) {
    LinkedList *list = linkedListCreate();
    for (int i = 0; i < n; i++) {
        printf("%d,", a[i]);
        linkedListAddAtTail(list, a[i]);
    }
    printf("\n");
    return list;
}