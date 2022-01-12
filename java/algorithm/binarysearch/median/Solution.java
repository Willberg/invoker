//4. 寻找两个正序数组的中位数 https://leetcode-cn.com/problems/median-of-two-sorted-arrays/
package algorithm.binarysearch.median;

// 划分数组, 将两个数组划分为两部分,前一部分包含nums1[0...i-1]和nums2[0...j-1],
// 再使用二分查找法,找到最后的i和j将两个数组分成均等的两部分, 最后根据总数的奇偶确定中位数
public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length, n = nums2.length;
        int left = 0, right = m;
        int median1 = 0, median2 = 0;

        while (left <= right) {
            // 前一部分包含nums1[0...i-1] 和nums2[0...j-1]
            int i = (left + right) / 2;
            int j = (m + n + 1) / 2 - i;

            int numsis1 = i == 0 ? Integer.MIN_VALUE : nums1[i - 1];
            int numsj = j == n ? Integer.MAX_VALUE : nums2[j];
            if (numsis1 <= numsj) {
                int numsi = i == m ? Integer.MAX_VALUE : nums1[i];
                int numsjs1 = j == 0 ? Integer.MIN_VALUE : nums2[j - 1];
                median1 = Math.max(numsis1, numsjs1);
                median2 = Math.min(numsi, numsj);
                left = i + 1;
            } else {
                right = i - 1;
            }
        }
        return ((m + n) & 1) == 0 ? (median1 + median2) / 2.0 : median1;
    }
}
