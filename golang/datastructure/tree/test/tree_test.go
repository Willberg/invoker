package test

import (
	"fmt"
	. "golang/datastructure/tree/redblacktree"
	"testing"
)

func TestRedBlackTree(t *testing.T) {
	tree := NewWithIntComparator()
	for i := 0; i < 50; i++ {
		tree.Insert(i, i)
	}
	for i, j := 1, 0; j < 25; i, j = i+2, j+1 {
		fmt.Println(i, j)
		tree.Delete(i)
		fmt.Println(tree.String(true))
	}
	if node, found := tree.Ceiling(1); found {
		fmt.Println(node.Value)
	}
	if node, found := tree.Floor(5); found {
		fmt.Println(node.Value)
	}
	for i, j := 0, 0; j < 25; i, j = i+2, j+1 {
		fmt.Println(i, j)
		tree.Delete(i)
		fmt.Println(tree.String(true))
	}
	if tree.Size() == 0 {
		tree.Insert(100, 100)
		if node, found := tree.Get(100); found {
			fmt.Println(tree.SubTreeString(node, true))
		}
	}
}
