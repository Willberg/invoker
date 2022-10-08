//
// Created by john on 2022/10/7.
//

#include <stdio.h>
#include "../../skiplist/skiplist.h"

int intn(int n) {
    int mx = RAND_MAX - RAND_MAX % n;
    int r = rand();
    while (r >= mx) {
        r = rand();
    }
    return r % n;
}

void shuffle(int *vs, int n) {
    while (n > 0) {
        int r = intn(n);
        int t = vs[r];
        vs[r] = vs[n - 1];
        vs[n - 1] = t;
        n--;
    }
}

void test_skip_list(int total, int size, int gap, float rate) {
    int vs[total];
    for (int i = 0; i < total; i++) {
        vs[i] = i * gap + intn(gap);
    }
    shuffle(vs, total);
    Skiplist *spl = skiplistCreate();
    for (int i = 0; i < total; i++) {
        skiplistAdd(spl, vs[i]);
        printf("%d, ", vs[i]);
    }
    printf("\nlevel = %d\n", spl->level);
    for (Node *p = spl->head->forward[0]; p; p = p->forward[0]) {
        printf("(%d, %d),", p->val, p->size);
    }
    printf("\n");
    int *checkVals = (int *) malloc(size * sizeof(int));
    int n = (int) (rate * (float) size);
    for (int i = 0, j = 0; i < size; i++) {
        if (i < n) {
            checkVals[i] = vs[i];
        } else {
            checkVals[i] = (total + j) * gap + intn(gap);
            j++;
        }
        printf("%d, ", checkVals[i]);
    }
    printf("\n");
    for (int i = 0; i < size; i++) {
        if (skiplistSearch(spl, checkVals[i])) {
            printf("(%d, %s),", checkVals[i], "true");
        } else {
            printf("(%d, %s),", checkVals[i], "false");
        }
    }
    printf("\n");
    for (int i = 0; i < size; i++) {
        if (skiplistErase(spl, checkVals[i])) {
            printf("(%d, %s),", checkVals[i], "true");
        } else {
            printf("(%d, %s),", checkVals[i], "false");
        }
    }
    printf("\nlevel = %d\n", spl->level);
    free(checkVals);
    for (Node *p = spl->head->forward[0]; p; p = p->forward[0]) {
        printf("(%d, %d),", p->val, p->size);
    }
    printf("\n");
    skiplistFree(spl);
}
