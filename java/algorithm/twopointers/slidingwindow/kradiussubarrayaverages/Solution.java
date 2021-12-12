// 2090. 半径为 k 的子数组平均值 https://leetcode-cn.com/problems/k-radius-subarray-averages/
package algorithm.twopointers.slidingwindow.kradiussubarrayaverages;

public class Solution {
    public int[] getAverages(int[] nums, int k) {
        int n = nums.length;
        int[] avgs = new int[n];
        // 滑动窗口的和, 注意要用long,防止溢出
        long sum = 0;
        for (int i = 0; i < n; i++) {
            if (i < k || i + k >= n) {
                avgs[i] = -1;
            }

            sum += nums[i];
            if (i >= 2 * k) {
                avgs[i - k] = (int) (sum / (2 * k + 1));
                sum -= nums[i - 2 * k];
            }
        }
        return avgs;
    }
}
