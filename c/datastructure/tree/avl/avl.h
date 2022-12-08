#ifndef AVL_H
#define AVL_H

typedef struct AVL_Tree AVL_Tree;
typedef struct AVL_Tree Node;
typedef struct AVL_Tree* PtrToNode;
typedef struct AVL_Tree* Tree;

struct AVL_Tree {
    int value;
    int height;
    PtrToNode left;
    PtrToNode right;
};

Tree create_tree(void);
Tree make_empty(Tree);
PtrToNode find(Tree, int);
int tree_height(Tree);
Tree insert(Tree, int);
Tree delete_node(Tree, int);
PtrToNode find_smaller_max(Tree);
Tree rotate_right(Tree, PtrToNode);
Tree rotate_left(Tree, PtrToNode);
Tree rotate_right_left(Tree, PtrToNode);
Tree rotate_left_right(Tree, PtrToNode);
void print_tree(Tree, int, int);


#endif /* AVL_H */