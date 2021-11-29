// 5924. 网格图中机器人回家的最小代价 https://leetcode-cn.com/problems/minimum-cost-homecoming-of-a-robot-in-a-grid/
package algorithm.greedy.mincost;

// 最小花销路径上一定不包含相反操作(即同时出现向上和向下,向左和向右的操作); 如果出现必然有可以抵消操作的路径存在
// (r,c)→(r+1,c)→(r+1,c+1) 的开销: rowCost[r+1] + colCost[c+1], 且等于(r,c)→(r,c+1)→(r+1,c+1)的开销, 交换位置不变
// (r,c)→(r,c+1)→(r+x,c+1)→(r+x,c) 的开销为 colCost[c+1] +  rowCost[r+1] +...+rowCost[r+x] + colCost[c],
// (r,c)→(r+x,c) 的开销为rowCost[r+1] +...+rowCost[r+x], 前者开销必然大于后者开销
// 因此, (startX, startY) 直接移动到(homeX, homeY)开销最少
public class Solution {
    public int minCost(int[] startPos, int[] homePos, int[] rowCosts, int[] colCosts) {
        int cost = 0;
        int x = startPos[0], y = startPos[1];
        while (x < homePos[0]) {
            x++;
            cost += rowCosts[x];
        }

        while (x > homePos[0]) {
            x--;
            cost += rowCosts[x];
        }

        while (y < homePos[1]) {
            y++;
            cost += colCosts[y];
        }

        while (y > homePos[1]) {
            y--;
            cost += colCosts[y];
        }
        return cost;
    }
}
