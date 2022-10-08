//
// Created by john on 2022/10/7.
//

#include "skiplist.h"

static const int P = RAND_MAX >> 2;
static const int MAX_LEVEL = 32;

Node *nodeCreate(int val, int level) {
    Node *t = (Node *) malloc(sizeof(Node));
    t->val = val;
    t->size = level;
    t->forward = (Node **) malloc(level * sizeof(Node *));
    for (int i = 0; i < level; i++) {
        t->forward[i] = NULL;
    }
    return t;
}

Skiplist *skiplistCreate() {
    Skiplist *skiplist = (Skiplist *) malloc(sizeof(Skiplist));
    skiplist->level = 0;
    skiplist->head = nodeCreate(-1, MAX_LEVEL);
    srand(time(NULL));
    return skiplist;
}

static inline int randLevel() {
    int level = 1;
    while (level < MAX_LEVEL && rand() < P) {
        level++;
    }
    return level;
}

bool skiplistSearch(Skiplist *obj, int target) {
    Node *cur = obj->head;
    for (int i = obj->level - 1; i >= 0; i--) {
        while (cur->forward[i] && cur->forward[i]->val < target) {
            cur = cur->forward[i];
        }
    }
    cur = cur->forward[0];
    return cur && cur->val == target;
}

void skiplistAdd(Skiplist *obj, int num) {
    int lv = randLevel();
    Node *t = nodeCreate(num, lv);
    Node *cur = obj->head;
    Node *pre[MAX_LEVEL];
    for (int i = MAX_LEVEL - 1; i >= 0; i--) {
        while (cur->forward[i] && cur->forward[i]->val < num) {
            cur = cur->forward[i];
        }
        if (i < lv) {
            pre[i] = cur;
        }
    }
    for (int i = lv - 1; i >= 0; i--) {
        t->forward[i] = pre[i]->forward[i];
        pre[i]->forward[i] = t;
    }
    obj->level = max(obj->level, lv);
}

void nodeFree(Node *node) {
    if (node->forward) {
        free(node->forward);
        node->forward = NULL;
        node->size = 0;
        node->val = 0;
    }
    free(node);
}

bool skiplistErase(Skiplist *obj, int num) {
    Node *cur = obj->head;
    Node *pre[MAX_LEVEL];
    for (int i = MAX_LEVEL - 1; i >= 0; i--) {
        while (cur->forward[i] && cur->forward[i]->val < num) {
            cur = cur->forward[i];
        }
        pre[i] = cur;
    }
    cur = cur->forward[0];
    if (!cur || cur->val != num) {
        return false;
    }
    for (int i = cur->size - 1; i >= 0; i--) {
        pre[i]->forward[i] = cur->forward[i];
    }
    if (obj->level > 0 && !obj->head->forward[obj->level - 1]) {
        obj->level--;
    }
    nodeFree(cur);
    return true;
}


void skiplistFree(Skiplist *obj) {
    for (Node *cur = obj->head; cur;) {
        Node *t = cur;
        cur = cur->forward[0];
        nodeFree(t);
    }
    free(obj);
}