//33. 搜索旋转排序数组 https://leetcode-cn.com/problems/search-in-rotated-sorted-array/
package algorithm.binarysearch.rotatedarray;

// 将中间值与左边值比较找到有序的一边,在有序的一边进行比较检测是否存在, 无法确定时直接将左边下标右移一位
public class Solution {
    public int search(int[] nums, int target) {
        // 二分法
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[mid] > nums[low]) {
                // 左边有序
                if (target < nums[low] || target > nums[mid]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            } else if (nums[mid] < nums[low]) {
                // 右边有序
                if (target < nums[mid] || target > nums[high]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {
                // 无法确定哪一边有序
                low++;
            }
        }
        return -1;
    }
}
