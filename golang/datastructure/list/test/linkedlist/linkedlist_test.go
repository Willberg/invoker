package test

import (
	"github.com/stretchr/testify/assert"
	. "golang/datastructure/list/linkedlist"
	"testing"
)

func TestLinkedList(t *testing.T) {
	a := assert.New(t)
	list := Constructor()
	for i := 1; i <= 10; i++ {
		list.AddAtTail(i)
	}
	list.AddAtHead(0)
	a.Equal(list.Get(0), 0)
	list.DeleteAtIndex(5)
	a.Equal(list.Get(5), 6)
}

func BenchmarkLinkedList(b *testing.B) {
	list := Constructor()
	for i := 0; i < b.N; i++ {
		list.AddAtTail(i)
	}
}
