//
// Created by john on 2022/10/12.
//

#ifndef C_LISTCOMPONENTS_H
#define C_LISTCOMPONENTS_H

#include "uthash.h"

#define true 1
#define false 0

typedef int bool;

typedef struct ListNode {
    int val;
    struct ListNode *next;
} ListNode;

#endif //C_LISTCOMPONENTS_H
