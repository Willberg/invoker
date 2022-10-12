// 817. 链表组件 https://leetcode.cn/problems/linked-list-components/
package linklistcomponents

type ListNode struct {
	Val  int
	Next *ListNode
}

func numComponents(head *ListNode, nums []int) (ans int) {
	seen := make(map[int]struct{}, len(nums))
	for _, v := range nums {
		seen[v] = struct{}{}
	}
	for inSet := false; head != nil; head = head.Next {
		if _, ok := seen[head.Val]; !ok {
			inSet = false
		} else if !inSet {
			inSet = true
			ans++
		}
	}
	return
}
