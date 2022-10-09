//
// Created by john on 2022/10/8.
//

// 1206. 设计跳表 https://leetcode.cn/problems/design-skiplist/
#ifndef CPP_SKIPLIST_H
#define CPP_SKIPLIST_H

#include <iostream>
#include <random>
#include <vector>

using namespace std;

constexpr int MAX_LEVEL = 32;
constexpr double P = 0.25;

struct Node {
    int val;
    vector<Node *> forward;

    explicit Node(int _val, int level = MAX_LEVEL) : val(_val), forward(level, nullptr) {

    }
};

class Skiplist {
private:
    int level;
    Node *head;
    mt19937 gen{random_device{}()};
    uniform_real_distribution<double> dis;
public:
    Skiplist() : head(new Node(-1)), level(0), dis(0, 1) {

    }

    int randLevel() {
        int lv = 1;
        while (dis(gen) < P && lv < MAX_LEVEL) {
            lv++;
        }
        return lv;
    }

    bool search(int target) {
        Node *cur = head;
        for (int i = level - 1; i >= 0; i--) {
            while (cur->forward[i] && cur->forward[i]->val < target) {
                cur = cur->forward[i];
            }
        }
        cur = cur->forward[0];
        return cur && target == cur->val;
    }

    void add(int num) {
        int lv = randLevel();
        Node *t = new Node(num, lv);
        vector<Node *> pre(lv);
        Node *cur = head;
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
        level = max(level, lv);
    }

    bool erase(int num) {
        vector<Node *> pre(MAX_LEVEL);
        Node *cur = head;
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
        for (int i = cur->forward.size() - 1; i >= 0; i--) {
            pre[i]->forward[i] = cur->forward[i];
        }
        delete cur;
        if (level > 0 && !head->forward[level - 1]) {
            level--;
        }
        return true;
    }

    void print() {
        cout << "\nlevel = " << level << endl;
        for (Node *p = head->forward[0]; p != nullptr; p = p->forward[0]) {
            cout << "(" << p->val << "," << p->forward.size() << "),";
        }
        cout << endl;
    }
};


#endif //CPP_SKIPLIST_H
