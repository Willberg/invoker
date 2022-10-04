package main

import (
	"fmt"
	. "golang/datastructure/list/linkedlist"
)

func main() {
	list := Constructor()
	for i := 1; i <= 10; i++ {
		list.AddAtTail(i)
	}
	list.AddAtHead(0)
	fmt.Println(list.Get(0))
	list.DeleteAtIndex(5)
	fmt.Println(list.Get(5))
}
