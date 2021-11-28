// 5938. 找出数组排序后的目标下标 https://leetcode-cn.com/problems/find-target-indices-after-sorting-array/
package datastructure.array.dimension.findtargetindicesaftersortingarray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// 排序之后找位置
public class Solution {
    public List<Integer> targetIndices(int[] nums, int target) {
        Arrays.sort(nums);

        List<Integer> ansList = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                ansList.add(i);
            }
        }
        return ansList;
    }
}
