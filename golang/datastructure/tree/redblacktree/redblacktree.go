// 3. 设计红黑树 https://zh.wikipedia.org/wiki/%E7%BA%A2%E9%BB%91%E6%A0%91
package redblacktree

import "fmt"

const (
	black = true
	red   = false
	RED   = "\033[31m"
	BLACK = "\033[30m"
	RESET = "\033[0m"
)

type Node struct {
	Key, Value          interface{}
	color               bool
	Left, Right, Parent *Node
}

type Comparator func(a, b interface{}) int

type Tree struct {
	root       *Node
	size       int
	comparator Comparator
}

func NewWithComparator(comparator Comparator) *Tree {
	return &Tree{comparator: comparator}
}

func NewWithIntComparator() *Tree {
	return &Tree{comparator: func(a, b interface{}) int { return a.(int) - b.(int) }}
}

func (tree *Tree) Size() int {
	return tree.size
}

func (tree *Tree) Insert(key, value interface{}) {
	insertNode := Node{Key: key, Value: value, color: red}
	if tree.root == nil {
		// 在初始化的时候用于验证key是否是Comparator的类型，提前暴露问题
		tree.comparator(key, key)
		tree.root = &insertNode
	} else {
		loop, node := true, tree.root
		for loop {
			cmp := tree.comparator(key, node.Key)
			switch {
			case cmp == 0:
				node.Value = value
				return
			case cmp < 0:
				if node.Left == nil {
					node.Left = &insertNode
					loop = false
				} else {
					node = node.Left
				}
			case cmp > 0:
				if node.Right == nil {
					node.Right = &insertNode
					loop = false
				} else {
					node = node.Right
				}
			}
		}
		insertNode.Parent = node
	}
	tree.insertCase1(&insertNode)
	tree.size++
}

func (tree *Tree) Delete(key interface{}) {
	node, found := tree.Get(key)
	if !found {
		return
	}
	// 转化为在最多只有1棵子树的树上删除节点的问题
	if node.Left != nil && node.Right != nil {
		smallest := getSmallestNode(node.Right)
		node.Key, node.Value = smallest.Key, smallest.Value
		node = smallest
	}
	child := node.Left
	if node.Left == nil {
		child = node.Right
	}
	if node.color == black {
		node.color = nodeColor(child)
		tree.deleteCase1(node)
	}
	if node.Parent == nil {
		tree.root = child
	} else {
		if node == node.Parent.Left {
			node.Parent.Left = child
		} else {
			node.Parent.Right = child
		}
	}
	if child != nil {
		child.Parent = node.Parent
	}
	// 只有两个节点的红黑树，删除节点是根节点，子节点是红色，需要变成黑色
	if node.Parent == nil && child != nil {
		child.color = black
	}
	tree.size--
}

func (tree *Tree) Get(key interface{}) (*Node, bool) {
	p := tree.root
	for {
		if p == nil {
			return nil, false
		}
		cmp := tree.comparator(key, p.Key)
		if cmp == 0 {
			return p, true
		} else if cmp < 0 {
			p = p.Left
		} else {
			p = p.Right
		}
	}
}

func (tree *Tree) Clear() {
	tree.root = nil
	tree.size = 0
}

// 小于等于key的最大节点
func (tree *Tree) Floor(key interface{}) (floor *Node, found bool) {
	node := tree.root
	for node != nil {
		cmp := tree.comparator(key, node.Key)
		if cmp == 0 {
			return node, true
		} else if cmp < 0 {
			node = node.Left
		} else {
			floor, found = node, true
			node = node.Right
		}
	}
	return
}

// 大于等于key的最小节点
func (tree *Tree) Ceiling(key interface{}) (ceiling *Node, found bool) {
	node := tree.root
	for node != nil {
		cmp := tree.comparator(key, node.Key)
		if cmp == 0 {
			return node, true
		} else if cmp < 0 {
			ceiling, found = node, true
			node = node.Left
		} else {
			node = node.Right
		}
	}
	return
}

func (tree *Tree) String(color bool) string {
	return tree.SubTreeString(tree.root, color)
}

func (tree *Tree) SubTreeString(node *Node, color bool) string {
	if node == nil {
		return ""
	}
	s := "RedBlackTree\n"
	output(node, "", true, &s, color)
	return s
}

func output(node *Node, prefix string, isTail bool, str *string, color bool) {
	if node.Right != nil {
		newPrefix := prefix
		if isTail {
			newPrefix += "│   "
		} else {
			newPrefix += "    "
		}
		output(node.Right, newPrefix, false, str, color)
	}
	*str += prefix
	if isTail {
		*str += "└── "
	} else {
		*str += "┌── "
	}
	if color {
		if node.color {
			*str += BLACK + node.String() + RESET + "\n"
		} else {
			*str += RED + node.String() + RESET + "\n"
		}
	} else {
		*str += node.String() + "\n"
	}
	if node.Left != nil {
		newPrefix := prefix
		if isTail {
			newPrefix += "    "
		} else {
			newPrefix += "│   "
		}
		output(node.Left, newPrefix, true, str, color)
	}
}

func (node *Node) String() string {
	return fmt.Sprintf("%v", node.Key)
}

func (node *Node) grandParent() *Node {
	if node.Parent == nil {
		return nil
	}
	return node.Parent.Parent
}

func (node *Node) uncle() *Node {
	gp := node.grandParent()
	if gp == nil {
		return nil
	}
	if node.Parent == gp.Left {
		return gp.Right
	}
	return gp.Left
}

func (node *Node) sibling() *Node {
	if node.Parent == nil {
		return nil
	}
	if node == node.Parent.Left {
		return node.Parent.Right
	}
	return node.Parent.Left
}

func getSmallestNode(node *Node) *Node {
	p := node
	for p.Left != nil {
		p = p.Left
	}
	return p
}

func nodeColor(node *Node) bool {
	if node == nil {
		return black
	}
	return node.color
}

func (tree *Tree) rotateLeft(node *Node) {
	gp, parent := node.grandParent(), node.Parent
	parent.Right = node.Left
	if node.Left != nil {
		node.Left.Parent = parent
	}
	node.Left = parent
	parent.Parent = node
	if tree.root == parent {
		tree.root = node
	}
	node.Parent = gp
	if gp != nil {
		if gp.Left == parent {
			gp.Left = node
		} else {
			gp.Right = node
		}
	}
}

func (tree *Tree) rotateRight(node *Node) {
	gp, parent := node.grandParent(), node.Parent
	parent.Left = node.Right
	if node.Right != nil {
		node.Right.Parent = parent
	}
	node.Right = parent
	parent.Parent = node
	if tree.root == parent {
		tree.root = node
	}
	node.Parent = gp
	if gp != nil {
		if gp.Left == parent {
			gp.Left = node
		} else {
			gp.Right = node
		}
	}
}

func (tree *Tree) insertCase1(node *Node) {
	if node.Parent == nil {
		tree.root = node
		node.color = black
	} else {
		tree.insertCase2(node)
	}
}

func (tree *Tree) insertCase2(node *Node) {
	if node.Parent.color == red {
		tree.insertCase3(node)
	}
}

func (tree *Tree) insertCase3(node *Node) {
	if nodeColor(node.uncle()) == red {
		node.Parent.color = black
		node.uncle().color = black
		node.grandParent().color = red
		tree.insertCase1(node.grandParent())
	} else {
		tree.insertCase4(node)
	}
}

func (tree *Tree) insertCase4(node *Node) {
	if node == node.Parent.Right && node.Parent == node.grandParent().Left {
		tree.rotateLeft(node)
		node = node.Left
	} else if node == node.Parent.Left && node.Parent == node.grandParent().Right {
		tree.rotateRight(node)
		node = node.Right
	}
	tree.insertCase5(node)
}

func (tree *Tree) insertCase5(node *Node) {
	node.Parent.color = black
	node.grandParent().color = red
	// 已经隐含node.Parent == node.grandParent().Left
	if node == node.Parent.Left {
		tree.rotateRight(node.Parent)
	} else {
		tree.rotateLeft(node.Parent)
	}
}

func (tree *Tree) deleteCase1(node *Node) {
	if node.Parent == nil {
		node.color = black
		return
	}
	tree.deleteCase2(node)
}

func (tree *Tree) deleteCase2(node *Node) {
	if node.sibling().color == red {
		node.Parent.color, node.sibling().color = red, black
		if node == node.Parent.Left {
			tree.rotateLeft(node.sibling())
		} else {
			tree.rotateRight(node.sibling())
		}
	}
	tree.deleteCase3(node)
}

func (tree *Tree) deleteCase3(node *Node) {
	if node.Parent.color == black && node.sibling().color == black &&
		nodeColor(node.sibling().Left) == black && nodeColor(node.sibling().Right) == black {
		node.sibling().color = red
		tree.deleteCase1(node.Parent)
	} else {
		tree.deleteCase4(node)
	}
}

func (tree *Tree) deleteCase4(node *Node) {
	if node.Parent.color == red && nodeColor(node.sibling().Left) == black && nodeColor(node.sibling().Right) == black {
		node.Parent.color, node.sibling().color = black, red
	} else {
		tree.deleteCase5(node)
	}
}

func (tree *Tree) deleteCase5(node *Node) {
	if node.sibling().color == black {
		if node == node.Parent.Left && nodeColor(node.sibling().Left) == red && nodeColor(node.sibling().Right) == black {
			node.sibling().color, node.sibling().Left.color = red, black
			tree.rotateRight(node.sibling().Left)
		} else if node == node.Parent.Right && nodeColor(node.sibling().Left) == black && nodeColor(node.sibling().Right) == red {
			node.sibling().color, node.sibling().Right.color = red, black
			tree.rotateLeft(node.sibling().Right)
		}
	}
	tree.deleteCase6(node)
}

func (tree *Tree) deleteCase6(node *Node) {
	node.sibling().color, node.Parent.color = node.Parent.color, black
	if node == node.Parent.Left && nodeColor(node.sibling().Right) == red {
		node.sibling().Right.color = black
		tree.rotateLeft(node.sibling())
	} else if node == node.Parent.Right && nodeColor(node.sibling().Left) == red {
		node.sibling().Left.color = black
		tree.rotateRight(node.sibling())
	}
}
