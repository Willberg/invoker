// 2091. 从数组中移除最大值和最小值 https://leetcode-cn.com/problems/removing-minimum-and-maximum-from-array/
package algorithm.greedy.removingminimumandmaximumfromarray;

// 删除的三种可能:
// 在i<j的条件下
// 1.删除包含最小值和最大值的数组前缀,次数j+1
// 2.删除包含最小值和最大值的数组后缀,次数n-i
// 3.删除包含最小值的前缀和包含最大值的后缀,次数(i+1)+(n-j)
// 求三者最小值
public class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        int i = 0, j = 0;
        for (int p = 0; p < n; p++) {
            if (nums[p] < nums[i]) {
                i = p;
            }

            if (nums[p] > nums[j]) {
                j = p;
            }
        }

        if (i > j) {
            int tmp = i;
            i = j;
            j = tmp;
        }
        return Math.min(Math.min(j + 1, n - i), (i + 1) + (n - j));
    }
}
