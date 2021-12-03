//11. 盛最多水的容器  https://leetcode-cn.com/problems/container-with-most-water/
package algorithm.greedy.mostwater;

// 左右两个指针,每次移动高度较小的那个指针,并计算体积更新最大体积直到两个指针相等
public class Solution {
    public int maxArea(int[] height) {
        // l表示从0开始, r表示从n-1开始,一直计算容器体积,将较大体积一直更新area
        int n = height.length;
        int l = 0, r = n - 1, area = -1;
        while (l < r) {
            int curArea = Math.min(height[l], height[r]) * (r - l);
            area = Math.max(area, curArea);
            if (height[l] <= height[r]) {
                // 从左往右移动,体积可能变大
                l++;
            } else {
                // 从右往左移动,体积可能变大
                r--;
            }
        }
        return area;
    }
}
