package test

import (
	"fmt"
	. "golang/datastructure/list/skiplist"
	"math/rand"
	"testing"
)

func shuffle(a []int) {
	n := len(a)
	for n > 0 {
		r := rand.Intn(n)
		a[r], a[n-1] = a[n-1], a[r]
		n--
	}
}

func TestSkipList(t *testing.T) {
	total, size, gap, rate := 100, 100, 10, 0.9
	arr, checkArr := make([]int, total), make([]int, size)
	for i := 0; i < total; i++ {
		v := i*gap + rand.Intn(gap)
		arr[i] = v
	}
	shuffle(arr)
	n, j := int(rate*float64(size)), 0
	for i, v := range arr[:size] {
		if i < n {
			checkArr[i] = v
		} else {
			checkArr[i] = (total+j)*gap + rand.Intn(gap)
			j++
		}
	}
	s := Constructor()
	fmt.Print("Add:")
	for _, v := range arr {
		s.Add(v)
		fmt.Printf("%d,", v)
	}
	s.PrintSkipList()
	fmt.Printf("search:")
	for _, v := range checkArr {
		fmt.Printf("(%d,%v),", v, s.Search(v))
	}
	fmt.Printf("\nerase:")
	for _, v := range checkArr {
		fmt.Printf("(%d,%v),", v, s.Erase(v))
	}
	s.PrintSkipList()
}
