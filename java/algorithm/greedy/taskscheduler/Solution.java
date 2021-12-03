//621. 任务调度器 https://leetcode-cn.com/problems/task-scheduler/
package algorithm.greedy.taskscheduler;

// 统计每种任务的数量, 计算数量最多的任务的个数,
// 当任务间隔小于任务种类时tasks.length就是最短时间; 但任务间隔大于等于任务种类时, (n+1)*(max-1) + cnt就是最短时间
// (最后一组任务在执行完任务之后不需要等待时间了)
public class Solution {
    public int leastInterval(char[] tasks, int n) {
        // int[] arr表示26种任务的数量,按任务降序排序
        int[] arr = new int[26];
        for (char c : tasks) {
            arr[c - 'A']++;
        }

        // cnt表示任务数量最多的任务种数, max表示任务数量最多的任务的数量
        int cnt = 0, max = 0;
        for (int v : arr) {
            if (v > max) {
                max = v;
                cnt = 1;
            } else if (v == max) {
                cnt++;
            }
        }
        return Math.max(tasks.length, cnt + (n + 1) * (max - 1));
    }
}
