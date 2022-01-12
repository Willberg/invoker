//287. 寻找重复数 https://leetcode-cn.com/problems/find-the-duplicate-number/
package algorithm.binarysearch.findduplicate;

// 二分法,使用抽屉原理, 1-n个位置之间有n+1个数,必然至少有一个位置有多余一个数
// 如果每次对整个数组计算发现不大于mid的数量cnt,发现cnt>mid那么就表示数出现在左边,否则在右边
public class Solution {
    public int findDuplicate(int[] nums) {
        int l = 1, r = nums.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            // 用cnt表示包含小于等于mid的数的数量
            int cnt = 0;
            for (int num : nums) {
                if (num <= mid) {
                    cnt++;
                }
            }

            if (cnt <= mid) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return r;
    }

    // 另一种解法: 使用快慢指针,效率更高
    // 核心思想: 建立i->nums[i]的链表,则因为有重复数字target,则必然出现环,可以用快慢指针找到环的入口,即重复的数
    public int findDuplicate2(int[] nums) {
        // 快慢指针
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        fast = 0;
        while (fast != slow) {
            fast = nums[fast];
            slow = nums[slow];
        }
        return fast;
    }
}
