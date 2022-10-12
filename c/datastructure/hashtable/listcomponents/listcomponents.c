//
// Created by john on 2022/10/12.
//
// 817. 链表组件 https://leetcode.cn/problems/linked-list-components/
#include "listcomponents.h"

typedef struct {
    int key;
    UT_hash_handle hh;
} HashItem;

HashItem *hashFindItem(HashItem **h, int key) {
    HashItem *e = NULL;
    HASH_FIND_INT(*h, &key, e);
    return e;
}

bool hashAddItem(HashItem **h, int key) {
    if (hashFindItem(h, key)) {
        return false;
    }
    HashItem *e = (HashItem *) malloc(sizeof(HashItem));
    e->key = key;
    HASH_ADD_INT(*h, key, e);
    return true;
}

void hashFree(HashItem **h) {
    HashItem *cur = NULL, *t = NULL;
    HASH_ITER(hh, *h, cur, t) {
        HASH_DEL(*h, cur);
        free(cur);
    }
}

int numComponents(struct ListNode *head, int *nums, int n) {
    HashItem *seen = NULL;
    for (int i = 0; i < n; i++) {
        hashAddItem(&seen, nums[i]);
    }
    int ans = 0;
    for (bool inSet = false; head; head = head->next) {
        if (!hashFindItem(&seen, head->val)) {
            inSet = false;
        } else if (!inSet) {
            inSet = true;
            ans++;
        }
    }
    hashFree(&seen);
    return ans;
}