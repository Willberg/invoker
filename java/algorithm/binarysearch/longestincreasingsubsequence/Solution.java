//300. 最长递增子序列  https://leetcode-cn.com/problems/longest-increasing-subsequence/
package algorithm.binarysearch.longestincreasingsubsequence;

// 采用贪心加二分查找, 核心思想: 每次添加一个能让序列数量增加但最小的数
// 1. 如果当前数nums[i]大于数组的最后一个数arr[len-1],则增加序列长度,并添加到数组中
// 2. 否则, 通过二分查找已存在的序列中刚好大于自己的数并替换掉
public class Solution {
    public int lengthOfLIS(int[] nums) {
        int len = 1, n = nums.length;
        int[] arr = new int[n];
        arr[0] = nums[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] > arr[len - 1]) {
                arr[len++] = nums[i];
            } else {
                // 如果找不到,说明所有数都大于nums[i], 此时需要替换掉arr[0]的值
                int l = 0, r = len - 1, pos = -1;
                // 二分查找比当前值小的数的位置
                while (l <= r) {
                    int mid = l + (r - l) / 2;
                    if (arr[mid] < nums[i]) {
                        // 保证pos位置的数是小于nums[i]的最大数
                        pos = mid;
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
                // pos+1位置的数刚好大于nums[i]
                arr[pos + 1] = nums[i];
            }
        }
        return len;
    }
}
