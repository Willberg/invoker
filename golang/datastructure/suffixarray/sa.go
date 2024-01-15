// https://leetcode.cn/problems/largest-merge-of-two-strings
// https://oi-wiki.org/string/sa/
// 后缀数组, 倍增 + 基数排序， 时间复杂度： O(nlogn)

package suffixarray

import "fmt"

func buildSuffixArray(s string) []int {
	n := len(s)
	sa, rk, cnt := make([]int, n+1), make([]int, n+1), make([]int, max(128, n+1))
	for i := 1; i <= n; i++ {
		ch := int(s[i-1])
		rk[i] = ch
		cnt[ch]++
	}
	for i := 1; i < 128; i++ {
		cnt[i] += cnt[i-1]
	}
	for i := n; i >= 1; i-- {
		sa[cnt[rk[i]]] = i
		cnt[rk[i]]--
	}
	id, key1, oldrk := make([]int, n+1), make([]int, n+1), make([]int, n+1)
	cmp := func(x, y, w int) bool {
		if oldrk[x] != oldrk[y] {
			return false
		}
		a, b := 0, 0
		if x+w <= n {
			a = oldrk[x+w]
		}
		if y+w <= n {
			b = oldrk[y+w]
		}
		return a == b
	}
	for w, p, m := 1, 0, 127; ; w, m = w<<1, p {
		p = 0
		for i := n; i > n-w; i-- {
			p++
			id[p] = i
		}
		for i := 1; i <= n; i++ {
			if sa[i] > w {
				p++
				id[p] = sa[i] - w
			}
		}
		memset(cnt, 0)
		for i := 1; i <= n; i++ {
			key1[i] = rk[id[i]]
			cnt[key1[i]]++
		}
		for i := 1; i <= m; i++ {
			cnt[i] += cnt[i-1]
		}
		for i := n; i >= 1; i-- {
			sa[cnt[key1[i]]] = id[i]
			cnt[key1[i]]--
		}
		copy(oldrk, rk)
		p = 0
		for i := 1; i <= n; i++ {
			if !cmp(sa[i], sa[i-1], w) {
				p++
			}
			rk[sa[i]] = p
		}
		if p == n {
			break
		}
	}
	for i := range sa {
		sa[i]--
	}
	return sa[1:]
}

func memset(a []int, x int) {
	for i := range a {
		a[i] = x
	}
}

func largestMerge(word1 string, word2 string) string {
	m, n := len(word1), len(word2)
	s := word1 + "@" + word2 + "*"
	sa := buildSuffixArray(s)
	rk := make([]int, len(sa))
	for i, x := range sa {
		rk[x] = i
	}
	merge := make([]byte, 0, m+n)
	for i, j := 0, 0; i < m || j < n; {
		if i < m && rk[i] > rk[j+m+1] {
			merge = append(merge, word1[i])
			i++
		} else {
			merge = append(merge, word2[j])
			j++
		}
	}
	return string(merge)
}

func PrintLargetMerge(word1, word2 string) {
	fmt.Println(largestMerge(word1, word2))
}
