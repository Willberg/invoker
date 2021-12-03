//406. 根据身高重建队列 https://leetcode-cn.com/problems/queue-reconstruction-by-height/
package algorithm.greedy.queuereconstruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 矮个的人会看到所有高个的人, 先按h降序,k升序排序; 然后k值就是所处位置的索引值
public class Solution {
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (a, b) -> a[0] != b[0] ? b[0] - a[0] : a[1] - b[1]);

        List<int[]> list = new ArrayList<>();
        for (int[] p : people) {
            list.add(p[1], p);
        }
        return list.toArray(new int[list.size()][]);
    }
}
