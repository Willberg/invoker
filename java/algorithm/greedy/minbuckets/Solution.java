// 5923. 从房屋收集雨水需要的最少水桶数  https://leetcode-cn.com/problems/minimum-number-of-buckets-required-to-collect-rainwater-from-houses/
package algorithm.greedy.minbuckets;

// 从左到右优先保证在房子右边添加水桶,如果不能放置水桶就在左边放置水桶,如果都不能放置就放回-1
public class Solution {
    public int minimumBuckets(String street) {
        // 从左到右扫描, 遇到房子检查左右两边,如果右边为边界或是房子,就在左边检查是否为边界或是房子,是就返回-1,是水桶就跳过,是空位就添加水桶
        // 否则就检查左边是否是水桶或是边界,如果不是就在右边添加水桶,然后跳过水桶, 如果是水桶就跳过
        char[] arr = street.toCharArray();
        int n = arr.length;
        // B表示水桶
        int ans = 0;
        for(int i=0; i<n; i++) {
            if(arr[i] == 'H') {
                if(i+1==n || arr[i+1] == 'H') {
                    if(i==0 || arr[i-1]=='H') {
                        return -1;
                    } else if(arr[i-1]=='.') {
                        arr[i-1] = 'B';
                        ans++;
                    }
                } else {
                    if(i==0 || arr[i-1]!='B') {
                        arr[i+1] = 'B';
                        ans++;
                        i++;
                    }
                }
            }
        }
        return ans;
    }
}
