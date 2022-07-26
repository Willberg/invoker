// 1206. 设计跳表 https://leetcode.cn/problems/design-skiplist/
package main

import (
	"fmt"
	"math/rand"
)

const (
	p        = 0.25
	maxLevel = 32
)

type Node struct {
	val     int
	forward []*Node
}

type Skiplist struct {
	head  *Node
	level int
}

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}

func randLevel() int {
	lv := 1
	for lv < maxLevel && rand.Float64() < p {
		lv++
	}
	return lv
}

func Constructor() Skiplist {
	return Skiplist{&Node{-1, make([]*Node, maxLevel)}, 0}
}

func (s *Skiplist) Search(target int) bool {
	cur := s.head
	for i := s.level - 1; i >= 0; i-- {
		// 找到第i层比target小且最接近的节点
		for cur.forward[i] != nil && cur.forward[i].val < target {
			cur = cur.forward[i]
		}
	}
	cur = cur.forward[0]
	return cur != nil && cur.val == target
}

func (s *Skiplist) Add(num int) {
	lv := randLevel()
	cur, update := s.head, make([]*Node, lv)
	for i := 0; i < lv; i++ {
		update[i] = s.head
	}
	for i := s.level - 1; i >= 0; i-- {
		for cur.forward[i] != nil && cur.forward[i].val < num {
			cur = cur.forward[i]
		}
		if i < lv {
			update[i] = cur
		}
	}
	s.level = max(s.level, lv)
	newNode := &Node{num, make([]*Node, lv)}
	for i, node := range update {
		newNode.forward[i] = node.forward[i]
		node.forward[i] = newNode
	}
}

func (s *Skiplist) Erase(num int) bool {
	cur, update := s.head, make([]*Node, maxLevel)
	for i := s.level - 1; i >= 0; i-- {
		for cur.forward[i] != nil && cur.forward[i].val < num {
			cur = cur.forward[i]
		}
		update[i] = cur
	}
	cur = cur.forward[0]
	if cur == nil || cur.val != num {
		return false
	}
	for i := 0; i < s.level && update[i].forward[i] == cur; i++ {
		update[i].forward[i] = cur.forward[i]
	}
	for s.level > 0 && s.head.forward[s.level-1] == nil {
		s.level--
	}
	return true
}

func main() {
	s := Constructor()
	arr := []int{}
	for i := 0; i < 10; i++ {
		v := int(rand.Int31())
		arr = append(arr, v)
		s.Add(v)
	}
	for _, v := range arr {
		fmt.Printf("Add %d\n", v)
	}
	fmt.Printf("search %d: %v\n", arr[0], s.Search(arr[0]))
	fmt.Printf("erase %d: %v\n", arr[0], s.Erase(arr[0]))
	fmt.Printf("search %d: %v\n", arr[0], s.Search(arr[0]))
}
