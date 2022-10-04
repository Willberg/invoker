//
// Created by john on 2022/10/1.
//

#include "../priorityqueue/priorityqueue.h"
#include "../../linkedlist/linkedlist.h"

bool ListNodeLess(void *a, void *b) {
    return ((ListNode *) a)->val < ((ListNode *) b)->val;
}

ListNode *mergeKLists(ListNode **lists, int listsSize) {
    if (listsSize == 0) {
        return NULL;
    }
    PriorityQueue pq = createPriorityQueue(listsSize, ListNodeLess);
    for (int i = 0; i < listsSize; i++) {
        ListNode *l = lists[i];
        if (l) {
            push(&pq, l);
        }
    }
    ListNode *dummy = (ListNode *) malloc(sizeof(ListNode));
    dummy->next = NULL;
    ListNode *p = dummy;
    while (!isEmpty(&pq)) {
        ListNode *top = (ListNode *) pop(&pq);
        if (top->next) {
            push(&pq, top->next);
        }
        p->next = top;
        p = p->next;
    }
    releasePriorityQueue(&pq);
    p = dummy->next;
    free(dummy);
    return p;
}

LinkedList *createList(int *a, int n) {
    LinkedList *list = linkedListCreate();
    for (int i = 0; i < n; i++) {
        printf("%d,", a[i]);
        linkedListAddAtTail(list, a[i]);
    }
    printf("\n");
    return list;
}

// [[1,4,5],[1,3,4],[2,6]]
void test_priority_queue() {
    int a1[3] = {1, 4, 5}, a2[3] = {1, 3, 4}, a3[2] = {2, 6};
    LinkedList *l1 = createList(a1, 3), *l2 = createList(a2, 3), *l3 = createList(a3, 2);
    ListNode **lists = (ListNode **) malloc(3 * sizeof(ListNode *));
    lists[0] = l1->head->next;
    lists[1] = l2->head->next;
    lists[2] = l3->head->next;
    ListNode *t = mergeKLists(lists, 3), *p = t;
    while (p) {
        printf("%d,", p->val);
        ListNode *tmp = p;
        p = p->next;
        free(tmp);
    }
    free(lists);
    linkedListFree(l1);
    linkedListFree(l2);
    linkedListFree(l3);
}