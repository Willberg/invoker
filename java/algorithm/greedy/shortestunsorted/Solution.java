// 581. 最短无序连续子数组 https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray/
package algorithm.greedy.shortestunsorted;

// 分别用maxn和minn表示i位置前后的最大值和最小值,如果i位置的值小于之前的最大值或大于之后的最小值,那么这个值就是需要进行排序的值
public class Solution {
    public int findUnsortedSubarray(int[] nums) {
        int left = -1, right = -1, n = nums.length;
        // maxn表示i位置之前的最大值, minn表示i位置之后的最小值
        int maxn = -100000, minn = 100000;
        for (int i = 0; i < nums.length; i++) {
            // 从左到右
            if (maxn > nums[i]) {
                right = i;
            } else {
                maxn = nums[i];
            }

            // 从右到左
            if (minn < nums[n - i - 1]) {
                left = n - i - 1;
            } else {
                minn = nums[n - i - 1];
            }
        }

        return right == -1 ? 0 : right - left + 1;
    }
}
