package main

import (
	"fmt"
	. "golang/datastructure/queue/priorityqueue"
)

type ListNode struct {
	Val  int
	Next *ListNode
}

func mergeKLists(lists []*ListNode) *ListNode {
	n := len(lists)
	if n == 0 {
		return nil
	}
	pq := CreatePriorityQueue(n, func(a, b interface{}) bool {
		return a.(*ListNode).Val < b.(*ListNode).Val
	})

	for _, l := range lists {
		if l != nil {
			pq.Push(l)
		}
	}
	dummy := &ListNode{}
	p := dummy
	for !pq.IsEmpty() {
		t := pq.Pop().(*ListNode)
		if t.Next != nil {
			pq.Push(t.Next)
		}
		p.Next = t
		p = p.Next
	}
	return dummy.Next
}

func createListNodes(a []int) *ListNode {
	dummy := ListNode{}
	p := &dummy
	for _, v := range a {
		t := ListNode{Val: v}
		p.Next = &t
		p = p.Next
	}
	return dummy.Next
}

func main() {
	var lists []*ListNode
	arr := [][]int{{1, 4, 5}, {1, 3, 4}, {2, 6}}
	for _, a := range arr {
		lists = append(lists, createListNodes(a))
	}

	p := mergeKLists(lists)
	for p != nil {
		fmt.Printf("%d,", p.Val)
		p = p.Next
	}
}
