package test

import (
	"fmt"
	"golang/datastructure/hashtable/comap"
	"sync"
	"testing"
	"time"
)

func TestComap(t *testing.T) {
	cmap := comap.NewMyConCurrentMap()
	n := sync.WaitGroup{}
	n.Add(30)
	for i := 0; i < 20; i++ {
		go func() {
			fmt.Println("读取1")
			v, err := cmap.Get(1, 5*time.Second)
			if err != nil {
				fmt.Printf("%v", err)
				return
			}
			fmt.Println(v)
			fmt.Println("读取1 结束")
			n.Done()
		}()
	}
	time.Sleep(2 * time.Second)
	for i := 0; i < 10; i++ {
		go func() {
			fmt.Println("写入1")
			cmap.Put(1, 1)
			fmt.Println("写入1 结束")
			n.Done()
		}()
	}
	n.Wait()
}
