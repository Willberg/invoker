// 921. 使括号有效的最少添加 https://leetcode.cn/problems/minimum-add-to-make-parentheses-valid/
package main

import "fmt"

func minAddToMakeValid(s string) (ans int) {
	cnt := 0
	for _, ch := range s {
		if ch == '(' {
			cnt++
		} else {
			if cnt > 0 {
				cnt--
			} else {
				ans++
			}
		}
	}
	return ans + cnt
}

func main() {
	fmt.Println(minAddToMakeValid("))("))
}
