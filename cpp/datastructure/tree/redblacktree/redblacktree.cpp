//
//  main.cpp
//  redblacktree
//
//  Created by bill on 2023/2/24.
//

// 3. 设计红黑树 https://zh.wikipedia.org/wiki/%E7%BA%A2%E9%BB%91%E6%A0%91
#define RESET_COLOR   "\033[0m"
#define RED_COLOR     "\033[31m"
#define BLACK_COLOR     "\033[30m"

#define BLACK 1
#define RED 0
#include <iostream>

using namespace std;

struct Node {
    int value;
    bool color;
    struct Node *left, *right, *parent;
    
    Node() : value(0), color(RED), left(nullptr), right(nullptr), parent(nullptr) {}
    
    Node* grandparent() {
        if (parent == nullptr) {
            return nullptr;
        }
        return parent->parent;
    }
    
    Node* uncle() {
        Node* gp = grandparent();
        if(gp == nullptr) {
            return nullptr;
        }
        if (parent == gp->left) {
            return gp->right;
        }
        return gp->left;
    }
    
    Node* silbing() {
        if(parent == nullptr) {
            return nullptr;
        }
        if(parent->left == this) {
            return parent->right;
        }
        return parent->left;
    }
};

class redblacktree {
private:
    void rotate_left(Node *p) {
        Node *gp = p->grandparent();
        Node *parent = p->parent;
        parent->right = p->left;
        if(p->left != NIL) {
            p->left->parent = parent;
        }
        p->left = parent;
        parent->parent = p;
        if(root == parent) {
            root = p;
        }
        p->parent = gp;
        if(gp) {
            if(gp->left == parent) {
                gp->left = p;
            } else {
                gp->right = p;
            }
        }
    }
    
    void rotate_right(Node *p) {
        Node *gp = p->grandparent();
        Node *parent = p->parent;
        parent->left = p->right;
        if(p->right != NIL) {
            p->right->parent = parent;
        }
        p->right = parent;
        parent->parent = p;
        if (root == parent) {
            root = p;
        }
        p->parent = gp;
        if(gp) {
            if(gp->left == parent) {
                gp->left = p;
            } else {
                gp->right = p;
            }
        }
    }
    
    bool contains(Node *p, int data) {
        if (p == NIL) {
            return false;
        }
        if(p->value == data) {
            return true;
        } else if(p->value > data) {
            return contains(p->left, data);
        } else {
            return contains(p->right, data);
        }
    }
    
    Node* find(Node *p, int data) {
        if (p == NIL) {
            return nullptr;
        }
        if(p->value == data) {
            return p;
        } else if(p->value > data) {
            return find(p->left, data);
        } else {
            return find(p->right, data);
        }
    }
    
    void inorder(Node *p) {
        if (p == NIL) {
            return;
        }
        if(p->left) {
            inorder(p->left);
        }
        cout << p->value<< " ";
        if(p->right) {
            inorder(p->right);
        }
    }
    
    string outputColor(bool color) {
        return color?"BLACK":"RED";
    }
    
    void insert(Node *p, int data) {
        if(p->value >= data) {
            if(p->left != NIL) {
                insert(p->left, data);
            } else {
                Node *node = new Node();
                node->value = data;
                node->left = node->right = NIL;
                p->left = node;
                node->parent = p;
                insert_case(node);
            }
        } else {
            if(p->right != NIL) {
                insert(p->right, data);
            } else {
                Node *node = new Node();
                node->value = data;
                node->left = node->right = NIL;
                p->right = node;
                node->parent = p;
                insert_case(node);
            }
        }
    }
    
    void insert_case(Node *p) {
        if(p->parent == nullptr) {
            root = p;
            p->color = BLACK;
            return;
        }
        if(p->parent->color == RED) {
            if(p->uncle()->color == RED) {
                p->parent->color = p->uncle()->color = BLACK;
                p->grandparent()->color = RED;
                insert_case(p->grandparent());
            } else {
                if (p == p->parent->right && p->grandparent()->left == p->parent) {
                    rotate_left(p);
                    p->color = BLACK;
                    p->parent->color = RED;
                    rotate_right(p);
                } else if(p == p->parent->left && p->grandparent()->right == p->parent) {
                    rotate_right(p);
                    p->color = BLACK;
                    p->parent->color = RED;
                    rotate_left(p);
                } else if(p == p->parent->left && p->grandparent()->left == p->parent) {
                    p->parent->color = BLACK;
                    p->grandparent()->color = RED;
                    rotate_right(p->parent);
                } else if(p == p->parent->right && p->grandparent()->right == p->parent) {
                    p->parent->color = BLACK;
                    p->grandparent()->color = RED;
                    rotate_left(p->parent);
                }
            }
        }
    }
    
    Node* getSmallestChild(Node* p) {
        if(p->left == NIL) {
            return p;
        }
        return getSmallestChild(p->left);
    }
    
    bool delete_child(Node *p, int data) {
        if(p->value > data) {
            if(p->left == NIL) {
                return false;
            }
            return delete_child(p->left, data);
        } else if (p->value < data) {
            if(p->right == NIL) {
                return false;
            }
            return delete_child(p->right, data);
        } else {
            if(p->right == NIL) {
                delete_one_child(p);
                return true;
            }
            Node *smallest = getSmallestChild(p->right);
            swap(p->value, smallest->value);
            delete_one_child(smallest);
            return true;
        }
    }
    
    void delete_one_child(Node *p) {
        if (p->parent == nullptr && p->left == NIL && p->right == NIL) {
            root = nullptr;
            delete p;
            return;
        }
        Node *child = p->left == NIL? p->right : p->left;
        if (p->parent == nullptr) {
            delete p;
            root = child;
            child->parent = nullptr;
            child->color = BLACK;
            return;
        }
        if(p->parent->left == p) {
            p->parent->left = child;
        } else {
            p->parent->right = child;
        }
        child->parent = p->parent;
        if (p->color == BLACK) {
            if(child->color == RED) {
                child->color = BLACK;
            } else {
                delete_case(child);
            }
        }
        delete p;
    }
    
    void delete_case(Node *p) {
        if (p->parent == nullptr) {
            p->color = BLACK;
            return;
        }
        if (p->silbing()->color == RED) {
            p->parent->color = RED;
            p->silbing()->color = BLACK;
            if (p == p->parent->left) {
                rotate_left(p->parent);
            } else {
                rotate_right(p->parent);
            }
        }
        if(p->parent->color == BLACK && p->silbing()->color == BLACK
           && p->silbing()->left->color == BLACK && p->silbing()->right->color == BLACK) {
            p->silbing()->color = RED;
            delete_case(p->parent);
        } else if (p->parent->color == RED && p->silbing()->color == BLACK
                   && p->silbing()->left->color == BLACK && p->silbing()->right->color == BLACK) {
            p->silbing()->color = RED;
            p->parent->color = BLACK;
        } else {
            if(p->silbing()->color == BLACK) {
                if (p == p->parent->left && p->silbing()->left->color == RED
                    && p->silbing()->right->color == BLACK) {
                    p->silbing()->color = RED;
                    p->silbing()->left->color = BLACK;
                    rotate_right(p->silbing()->left);
                } else if(p == p->parent->right && p->silbing()->right->color == RED
                          && p->silbing()->left->color == BLACK) {
                    p->silbing()->color = RED;
                    p->silbing()->right->color = BLACK;
                    rotate_left(p->silbing()->right);
                }
                p->silbing()->color = p->parent->color;
                p->parent->color = BLACK;
                if(p == p->parent->left) {
                    p->silbing()->right->color = BLACK;
                    rotate_left(p->silbing());
                } else {
                    p->silbing()->left->color = BLACK;
                    rotate_right(p->silbing());
                }
            }
        }
    }
    
    void output(Node *node, string prefix, bool isTail, string *s, bool color) {
        if (node->right != NIL) {
            string newPrefix = prefix;
            if (isTail) {
                newPrefix += "│   ";
            } else {
                newPrefix += "    ";
            }
            output(node->right, newPrefix, false, s, color);
        }
        *s += prefix;
        if (isTail) {
            *s += "└── ";
        } else {
            *s += "┌── ";
        }
        if(color) {
            if (node->color) {
                *s += BLACK_COLOR + to_string(node->value) + RESET_COLOR + "\n";
            } else {
                *s += RED_COLOR + to_string(node->value) + RESET_COLOR + "\n";
            }
        } else {
            *s += to_string(node->value) + "\n";
        }
        if (node->left != NIL) {
            string newPrefix = prefix;
            if(isTail) {
                newPrefix += "    ";
            } else {
                newPrefix += "│   ";
            }
            output(node->left, newPrefix, true, s, color);
        }
    }
    
    void delete_tree(Node *p) {
        if(!p || p == NIL) {
            return;
        }
        delete_tree(p->left);
        delete_tree(p->right);
        delete p;
    }
    
    Node *root, *NIL;
public:
    redblacktree() {
        NIL = new Node();
        NIL->color = BLACK;
        root = nullptr;
    }
    
    ~redblacktree() {
        if(root) {
            delete_tree(root);
        }
        delete NIL;
    }
    
    void inorder() {
        if (root == nullptr) {
            return;
        }
        inorder(root);
        cout << endl;
    }
    
    void printTree(Node *p, bool color) {
        if (p == nullptr || p == NIL) {
            cout << "tree is empty." << endl;
            return;
        }
        string s = "RedBlackTree\n";
        output(p, "", true, &s, color);
        cout << s << endl;
    }
    
    void printAllTree(bool color) {
        if (root == nullptr || root == NIL) {
            cout << "tree is empty." << endl;
            return;
        }
        string s = "RedBlackTree\n";
        output(root, "", true, &s, color);
        cout << s << endl;
    }
    
    bool contains(int x) {
        if (root == nullptr) {
            return false;
        }
        return contains(root, x);
    }
    
    Node* find(int x) {
        if (root == nullptr) {
            return nullptr;
        }
        return find(root, x);
    }
    
    void insert(int x) {
        if(root == nullptr) {
            root = new Node();
            root->value = x;
            root->color = BLACK;
            root->left = root->right = NIL;
            return;
        }
        insert(root, x);
    }
    
    bool delete_value(int data) {
        if (root == nullptr) {
            return false;
        }
        return delete_child(root, data);
    }
};

int main(int argc, const char * argv[]) {
    redblacktree rbt;
    for(int i=0; i<50; i++) {
        rbt.insert(i);
        rbt.printAllTree(true);
    }
    rbt.insert(-100);
    rbt.insert(0);
    for (int i=1, j=0; j<25; j++, i+=2) {
        rbt.delete_value(i);
        rbt.printAllTree(true);
    }
    rbt.printAllTree(false);
    Node* p = rbt.find(8);
    rbt.printTree(p, false);
    rbt.printTree(p, true);
    return 0;
}