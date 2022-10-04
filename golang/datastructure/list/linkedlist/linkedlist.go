//707. 设计链表 https://leetcode.cn/problems/design-linked-list/
package linkedlist

type node struct {
	val        int
	prev, next *node
}

type MyLinkedList struct {
	size       int
	head, tail *node
}

func Constructor() MyLinkedList {
	head, tail := &node{}, &node{}
	head.next = tail
	tail.prev = head
	return MyLinkedList{0, head, tail}
}

func (this *MyLinkedList) find(index int) *node {
	if index < 0 || index >= this.size {
		return nil
	}
	if index < this.size/2 {
		p := this.head.next
		for i := 0; i < index; i++ {
			p = p.next
		}
		return p
	} else {
		p := this.tail
		for i := 0; i < this.size-index; i++ {
			p = p.prev
		}
		return p
	}
}

func (this *MyLinkedList) Get(index int) int {
	p := this.find(index)
	if p == nil {
		return -1
	}
	return p.val
}

func (this *MyLinkedList) AddAtHead(val int) {
	this.AddAtIndex(0, val)
}

func (this *MyLinkedList) AddAtTail(val int) {
	this.AddAtIndex(this.size, val)
}

func (this *MyLinkedList) AddAtIndex(index int, val int) {
	if index > this.size {
		return
	}
	if index <= 0 {
		t := &node{val, this.head, this.head.next}
		this.head.next = t
		t.next.prev = t
		this.size++
		return
	}
	if index == this.size {
		t := &node{val, this.tail.prev, this.tail}
		t.prev.next = t
		this.tail.prev = t
		this.size++
		return
	}
	p := this.find(index)
	t := &node{val, p.prev, p}
	p.prev.next = t
	p.prev = t
	this.size++
}

func (this *MyLinkedList) DeleteAtIndex(index int) {
	p := this.find(index)
	if p == nil {
		return
	}
	p.prev.next = p.next
	p.next.prev = p.prev
	this.size--
}
