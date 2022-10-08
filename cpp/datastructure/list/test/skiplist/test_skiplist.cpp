//
// Created by john on 2022/10/8.
//
#include "../../skiplist/skiplist.h"


int intn(int n, mt19937 &gen) {
    uniform_int_distribution<int> dis(0, n - 1);
    return dis(gen);
}

void shuffle(vector<int> &a, mt19937 &gen) {
    int n = a.size();
    while (n > 0) {
        int r = intn(n, gen);
        swap(a[r], a[n - 1]);
        n--;
    }
}

void test_skip_list(int total, int size, int gap, float rate) {
    mt19937 gen{random_device{}()};
    int n = (int) (rate * (float) size);
    vector<int> vals(total), checkVals(size);
    for (int i = 0; i < total; i++) {
        vals[i] = i * gap + intn(gap, gen);
    }
    shuffle(vals, gen);
    auto spl = new Skiplist();
    for (int v : vals) {
        spl->add(v);
        cout << v << ",";
    }
    spl->print();
    for (int i = 0, j = 0; i < size; i++) {
        if (i < n) {
            checkVals[i] = vals[i];
        } else {
            checkVals[i] = (total + j) * gap + intn(gap, gen);
            j++;
        }
    }
    for (int v: checkVals) {
        if (spl->search(v)) {
            cout << "(" << v << ", true),";
        } else {
            cout << "(" << v << ", false),";
        }
    }
    cout << endl;
    for (int v: checkVals) {
        if (spl->erase(v)) {
            cout << "(" << v << ", true),";
        } else {
            cout << "(" << v << ", false),";
        }
    }
    spl->print();
}

