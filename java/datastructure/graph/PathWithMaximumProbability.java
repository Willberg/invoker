//1514. 概率最大的路径 https://leetcode-cn.com/problems/path-with-maximum-probability/
package datastructure.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

// Dijkstra算法, 将所有节点分成两类：已确定从起点到当前点的最短路长度的节点，以及未确定从起点到当前点的最短路长度的节点（下面简称「未确定节点」和「已确定节点」）。
// 图采用邻接表表示
// 起点到节点A的距离加上节点A到节点B的距离跟起点到节点B的距离比较,更新节点B的距离
// 在本题中,起点的概率设置为1, 用起点到u点的概率*u到v点的概率跟起点到v点的概率比较,如果前者较大,就更新v点的概率,并加入队列,从该点继续处理
public class PathWithMaximumProbability {
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        List<List<Pair>> graph = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new LinkedList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            graph.get(edges[i][0]).add(new Pair(edges[i][1], succProb[i]));
            graph.get(edges[i][1]).add(new Pair(edges[i][0], succProb[i]));
        }

        double[] ans = new double[n];
        ans[start] = 1;
        boolean[] visited = new boolean[n];
        PriorityQueue<Pair> q = new PriorityQueue<>();
        q.offer(new Pair(start, 1));
        while (!q.isEmpty()) {
            Pair top = q.poll();
            int u = top.node;
            if (visited[u]) {
                continue;
            }

            visited[u] = true;
            if (u == end) {
                return ans[u];
            }
            List<Pair> list = graph.get(u);
            for (Pair p : list) {
                int v = p.node;
                double probility = p.probility;
                if (ans[v] < ans[u] * probility) {
                    ans[v] = ans[u] * probility;
                    q.offer(new Pair(v, ans[v]));
                }
            }
        }
        return ans[end];
    }

    class Pair implements Comparable<Pair> {
        int node;
        double probility;

        public Pair(int node, double probility) {
            this.node = node;
            this.probility = probility;
        }

        @Override
        public int compareTo(Pair o) {
            if (this.probility == o.probility) {
                return 0;
            } else {
                return this.probility - o.probility > 0 ? -1 : 1;
            }
        }
    }
}
