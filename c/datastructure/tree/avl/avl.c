// 2. 设计平衡二叉搜索树 https://zh.wikipedia.org/wiki/AVL%E6%A0%91
#include "avl.h"
#include <stdio.h>
#include <stdlib.h>

Tree create_tree(void) {
    return NULL;
}

Tree make_empty(Tree root) {
    if(root != NULL) {
        make_empty(root->left);
        make_empty(root->right);
        free(root);
    }
    return NULL;
}

PtrToNode find(Tree root, int value) {
    if (root == NULL) {
        return NULL;
    } else if (root->value == value) {
        return root;
    } else if (root->value > value) {
        return find(root->left, value);
    } else {
        return find(root->right, value);
    }
}

int tree_height(Tree root) {
    if (root == NULL) {
        return 0;
    }
    return root->height;
}

Tree insert(Tree root, int value) {
    if (root == NULL) {
        root = (Tree) malloc(sizeof(Node));
        if (root == NULL) {
            printf("Not enough space.\n");
            return NULL;
        }
        root->value = value;
        root->left = NULL;
        root->right = NULL;
    } else if (root->value == value) {
        printf("Value was already in the tree.\n");
    } else if (root->value > value) {
        root->left = insert(root->left, value);
        if(tree_height(root->left) - tree_height(root->right) == 2) {
            if (value < root->left->value) {
                root = rotate_right(root, root->left);
            } else {
                root = rotate_left_right(root, root->left);
            }
        }
    } else {
        root->right = insert(root->right, value);
        if (tree_height(root->right) - tree_height(root->left) == 2) {
            if (value > root->right->value) {
                root = rotate_left(root, root->right);
            } else {
                root = rotate_right_left(root, root->right);
            }
        }
    }
    int left_height = tree_height(root->left);
    int right_height = tree_height(root->right);
    root->height = left_height > right_height?left_height+1:right_height+1;
    return root;
}

Tree delete_node(Tree root, int value) {
    if(root == NULL) {
        printf("Empty Tree.\n");
        return NULL;
    }
    if (value == root->value) {
        if(root->left == NULL) {
            PtrToNode right = root->right;
            free(root);
            root = right;
        } else if (root->right == NULL) {
            PtrToNode left = root->left;
            free(root);
            root = left;
        } else {
            PtrToNode sm_max = find_smaller_max(root);
            int sm_value = sm_max->value;
            root->value = sm_value;
            root->left = delete_node(root->left, sm_value);
            if(tree_height(root->right) - tree_height(root->left) == 2) {
                if (tree_height(root->right->right) >= tree_height(root->right->left)) {
                    root = rotate_left(root, root->right);
                } else {
                    root = rotate_right_left(root, root->right);
                }
            }
        }
    } else if (value < root->value) {
        root->left = delete_node(root->left, value);
        if(tree_height(root->right)-tree_height(root->left) == 2) {
            if(tree_height(root->right->right) >= tree_height(root->right->left)) {
                root = rotate_left(root, root->right);
            } else {
                root = rotate_right_left(root, root->right);
            }
        }
    } else {
        root->right = delete_node(root->right, value);
        if(tree_height(root->left)-tree_height(root->right) == 2) {
            if(tree_height(root->left->left) >= tree_height(root->left->right)) {
                root = rotate_right(root, root->left);
            } else {
                root = rotate_left_right(root, root->left);
            }
        }
    }
    if (root != NULL) {
        int left_height = tree_height(root->left);
        int right_height = tree_height(root->right);
        root->height = left_height > right_height?left_height+1:right_height+1;
    }
    return root;
}

PtrToNode find_smaller_max(Tree root) {
    PtrToNode iterator = root->left;
    while(iterator->right != NULL) {
        iterator = iterator->right;
    }
    return iterator;
}

Tree rotate_right(Tree root, PtrToNode node){
    root->left = node->right;
    node->right = root;
    int left_height = tree_height(root->left);
    int right_height = tree_height(root->right);
    root->height = left_height>right_height?left_height+1:right_height+1;
    left_height = tree_height(node->left);
    right_height = tree_height(node->right);
    node->height = left_height>right_height?left_height+1:right_height+1;
    return node;
}

Tree rotate_left(Tree root, PtrToNode node){
    root->right = node->left;
    node->left = root;
    int left_height = tree_height(root->left);
    int right_height = tree_height(root->right);
    root->height = left_height > right_height?left_height+1:right_height+1;
    left_height = tree_height(node->left);
    right_height = tree_height(node->right);
    node->height = left_height>right_height?left_height+1:right_height+1;
    return node;
}

Tree rotate_right_left(Tree root, PtrToNode node){
    root->right = rotate_right(node, node->left);
    return rotate_left(root, root->right);
}

Tree rotate_left_right(Tree root, PtrToNode node){
    root->left = rotate_left(node, node->right);
    return rotate_right(root, root->left);
}

void print_tree(Tree t, int type, int level) {
    if (t == NULL) {
        return;
    }
    print_tree(t->right, 2, level+1);
    switch(type) {
        case 0:
            printf("%2d\n", t->value);
            break;
        case 1:
            for(int i=0; i<level; i++) {
                printf("\t");
            }
            printf("\\ %2d\n", t->value);
            break;
        case 2:
            for(int i=0; i<level; i++) {
                printf("\t");
            }
            printf("/ %2d\n", t->value);
            break;
    }
    print_tree(t->left, 1, level+1);
}

void test_avl() {
    printf("Test AVL Tree:\n");
    printf("Commands:\n");
    printf("[0] exit\n");
    printf("[1] insert value into the tree\n");
    printf("[2] delete value from the tree\n");
    printf("[3] get height from the tree\n");
    
    Tree tree = create_tree();
    print_tree(tree, 0, 0);
    int exit = 1;
    while(exit) {
        int cmd = -1;
        scanf("%d", &cmd);
        if (cmd == 0) {
            exit = 0;
        } else if(cmd == 1) {
            int val;
            scanf("%d", &val);
            tree = insert(tree, val);
            print_tree(tree, 0, 0);
            printf("\n");
        } else if (cmd == 2){
            int val;
            scanf("%d", &val);
            tree = delete_node(tree, val);
            print_tree(tree, 0, 0);
            printf("\n");
        } else if (cmd == 3) {
            int val;
            scanf("%d", &val);
            PtrToNode t = find(tree, val);
            printf("%d\n", tree_height(t));
        }
    }
}