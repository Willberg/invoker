// 55. 跳跃游戏 https://leetcode-cn.com/problems/jump-game/
package algorithm.greedy.jumpgame;

// 每一次移动都计算目前能跳到的最右边的位置,并判断是否大于等于最后的下标,否则就向右移动一步,但不能超过能跳动的最右边的位置
public class Solution {
    public boolean canJump(int[] nums) {
        // rightMost表示右边最远能跳到的位置
        int rightMost = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i <= rightMost) {
                rightMost = Math.max(rightMost, i + nums[i]);
            } else {
                return false;
            }
            if (nums.length <= rightMost + 1) {
                return true;
            }
        }
        return false;
    }
}
