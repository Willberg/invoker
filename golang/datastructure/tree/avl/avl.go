// 设计平衡二叉搜索树 https://zh.wikipedia.org/wiki/AVL%E6%A0%91
package main

import "fmt"

type TreeNode struct {
	val    int
	height int
	Left   *TreeNode
	Right  *TreeNode
}

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}

func GetHeight(root *TreeNode) int {
	if root == nil {
		return 0
	}
	return root.height
}

func Find(root *TreeNode, val int) *TreeNode {
	if root == nil {
		return nil
	}
	if root.val == val {
		return root
	} else if val < root.val {
		return Find(root.Left, val)
	} else {
		return Find(root.Right, val)
	}
}

func findSmallerMax(root *TreeNode) *TreeNode {
	t := root.Left
	for t.Right != nil {
		t = t.Right
	}
	return t
}

func Add(root *TreeNode, val int) *TreeNode {
	if root == nil {
		root = &TreeNode{val: val}
	} else if root.val == val {
		fmt.Println("Val was already in the tree.")
		return root
	} else if val < root.val {
		root.Left = Add(root.Left, val)
		if GetHeight(root.Left)-GetHeight(root.Right) == 2 {
			if val < root.Left.val {
				root = rotateRight(root, root.Left)
			} else {
				root = rotateLeftRight(root, root.Left)
			}
		}
	} else {
		root.Right = Add(root.Right, val)
		if GetHeight(root.Right)-GetHeight(root.Left) == 2 {
			if val > root.Right.val {
				root = rotateLeft(root, root.Right)
			} else {
				root = rotateRightLeft(root, root.Right)
			}
		}
	}
	adjHeight(root)
	return root
}

func Delete(root *TreeNode, val int) *TreeNode {
	if root == nil {
		fmt.Println("Empty tree.")
		return nil
	} else if root.val == val {
		if root.Left == nil {
			root = root.Right
		} else if root.Right == nil {
			root = root.Left
		} else {
			sm := findSmallerMax(root)
			root.val = sm.val
			root.Left = Delete(root.Left, sm.val)
			if GetHeight(root.Right)-GetHeight(root.Left) == 2 {
				if GetHeight(root.Right.Right) >= GetHeight(root.Right.Left) {
					root = rotateLeft(root, root.Right)
				} else {
					root = rotateRightLeft(root, root.Right)
				}
			}
		}
	} else if val < root.val {
		root.Left = Delete(root.Left, val)
		if GetHeight(root.Right)-GetHeight(root.Left) == 2 {
			if GetHeight(root.Right.Right) >= GetHeight(root.Right.Left) {
				root = rotateLeft(root, root.Right)
			} else {
				root = rotateRightLeft(root, root.Right)
			}
		}
	} else {
		root.Right = Delete(root.Right, val)
		if GetHeight(root.Left)-GetHeight(root.Right) == 2 {
			if GetHeight(root.Left.Left) >= GetHeight(root.Left.Right) {
				root = rotateRight(root, root.Left)
			} else {
				root = rotateLeftRight(root, root.Left)
			}
		}
	}
	if root != nil {
		adjHeight(root)
	}
	return root
}

func adjHeight(nodes ...*TreeNode) {
	for _, t := range nodes {
		leftHeight, rightHeight := GetHeight(t.Left), GetHeight(t.Right)
		t.height = max(leftHeight, rightHeight) + 1
	}
}

func rotateLeft(root *TreeNode, node *TreeNode) *TreeNode {
	root.Right = node.Left
	node.Left = root
	adjHeight(root, node)
	return node
}

func rotateRight(root *TreeNode, node *TreeNode) *TreeNode {
	root.Left = node.Right
	node.Right = root
	adjHeight(root, node)
	return node
}

func rotateLeftRight(root *TreeNode, node *TreeNode) *TreeNode {
	root.Left = rotateLeft(node, node.Right)
	return rotateRight(root, root.Left)
}

func rotateRightLeft(root *TreeNode, node *TreeNode) *TreeNode {
	root.Right = rotateRight(node, node.Left)
	return rotateLeft(root, root.Right)
}

func PrintTree(root *TreeNode, t, level int) {
	if root == nil {
		return
	}
	PrintTree(root.Right, 2, level+1)
	switch t {
	case 0:
		fmt.Printf("%2d\n", root.val)
	case 1:
		for i := 0; i < level; i++ {
			fmt.Print("\t")
		}
		fmt.Printf("\\ %2d\n", root.val)
	case 2:
		for i := 0; i < level; i++ {
			fmt.Print("\t")
		}
		fmt.Printf("/ %2d\n", root.val)
	}
	PrintTree(root.Left, 1, level+1)
}

func main() {
	fmt.Println("Test AVL Tree:")
	fmt.Println("Commands:")
	fmt.Println("[0] exit")
	fmt.Println("[1] add value into the tree.")
	fmt.Println("[2] delete value from the tree.")
	fmt.Println("[3] get the height from the tree.")

	cmd := -1
	var tree *TreeNode
	for cmd != 0 {
		_, err := fmt.Scanf("%d", &cmd)
		if err != nil {
			break
		}
		switch cmd {
		case 1:
			var val int
			_, err := fmt.Scanf("%d", &val)
			if err != nil {
				break
			}
			tree = Add(tree, val)
			PrintTree(tree, 0, 0)
		case 2:
			var val int
			_, err := fmt.Scanf("%d", &val)
			if err != nil {
				break
			}
			tree = Delete(tree, val)
			PrintTree(tree, 0, 0)
		case 3:
			var val int
			_, err := fmt.Scanf("%d", &val)
			if err != nil {
				break
			}
			t := Find(tree, val)
			fmt.Println(GetHeight(t))
		}
	}
}
