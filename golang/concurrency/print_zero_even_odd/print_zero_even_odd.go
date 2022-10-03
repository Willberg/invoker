// 1116. 打印零与奇偶数 https://leetcode-cn.com/problems/print-zero-even-odd/
package main

import (
	"fmt"
	"log"
	"sync"
)

var (
	// 如果用无缓存通道会出现死锁, 可以考虑一下为什么
	zeroSema = make(chan int, 1)
	evenSema = make(chan int, 1)
	oddSema  = make(chan int, 1)
)

func printZero() {
	fmt.Print(0)
}

func printEven(v int) {
	if v <= 0 || v%2 != 0 {
		log.Fatal("this is not even")
	}
	fmt.Print(v)
}

func printOdd(v int) {
	if v <= 0 || v%2 == 0 {
		log.Fatal("this is not odd")
	}
	fmt.Print(v)
}

func zero(n int, w *sync.WaitGroup) {
	for i := 1; i <= n; i++ {
		<-zeroSema
		printZero()
		if (i & 1) == 1 {
			// 奇数
			oddSema <- 1
		} else {
			evenSema <- 2
		}
	}
	w.Done()
}

func even(n int, w *sync.WaitGroup) {
	for i := 1; i <= n; i++ {
		if (i & 1) == 0 {
			// 偶数
			<-evenSema
			printEven(i)
			zeroSema <- 0
		}
	}
	w.Done()
}

func odd(n int, w *sync.WaitGroup) {
	for i := 1; i <= n; i++ {
		if (i & 1) == 1 {
			// 奇数
			<-oddSema
			printOdd(i)
			zeroSema <- 0
		}
	}
	w.Done()
}

func printZeroEvenOdd(n int) {
	var w sync.WaitGroup
	w.Add(3)
	go zero(n, &w)
	go odd(n, &w)
	go even(n, &w)

	zeroSema <- 0
	w.Wait()
	close(zeroSema)
	close(oddSema)
	close(evenSema)
}

func main() {
	printZeroEvenOdd(5)
}
