    // 399. 除法求值 https://leetcode-cn.com/problems/evaluate-division/
package datastructure.graph.unionfind.evaluatedivision;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    private class UnionFind {
        private final int[] parent;

        // 指向父节点的权重,在这里表示a/b的比值k,即a = k*b (b为a的父节点)
        private final double[] weight;

        public UnionFind(int n) {
            this.parent = new int[n];
            this.weight = new double[n];
            // 默认每个节点的父节点都是自己,权重为1
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                weight[i] = 1.0d;
            }
        }

        // 处理x/y=value, x,y肯定可以用同一个第三方变量表示, 即可以指向同一个最终父节点
        public void union(int x, int y, double value) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) {
                return;
            }

            // 让x的最终父节点指向rootY,即让rootY成为rootX的最终父节点
            parent[rootX] = rootY;
            // weight[x]表示了x到最终父节点的权重乘积(路径压缩导致)
            weight[rootX] = weight[y] * value / weight[x];
        }

        // 路径压缩,将所有最终父节点相同的节点都直接指向最终父节点
        public int find(int x) {
            if (x != parent[x]) {
                int origin = parent[x];
                parent[x] = find(parent[x]);
                weight[x] *= weight[origin];
            }
            return parent[x];
        }

        // 如果x,y相连,则x,y之间可以通过共同的最终父节点来表示从而进行计算
        public double isConnected(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) {
                return weight[x] / weight[y];
            } else {
                return -1.0d;
            }
        }
    }

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int equationSize = equations.size();

        // 表达式有两个节点a,b, 并查集最多可能有2倍表达式数量的节点
        UnionFind unionFind = new UnionFind(2 * equationSize);

        // 表达式构成的两个字符串做映射
        Map<String, Integer> hash = new HashMap<String, Integer>(2 * equationSize);
        int id = 0;
        // 1.加入所有节点,构建并查集
        for (int i = 0; i < equationSize; i++) {
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);

            if (!hash.containsKey(a)) {
                hash.put(a, id);
                id++;
            }

            if (!hash.containsKey(b)) {
                hash.put(b, id);
                id++;
            }
            unionFind.union(hash.get(a), hash.get(b), values[i]);
        }

        // 2. 计算所有查询
        double[] ans = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String a = queries.get(i).get(0);
            String b = queries.get(i).get(1);

            if (!hash.containsKey(a) || !hash.containsKey(b)) {
                ans[i] = -1.0d;
            } else {
                ans[i] = unionFind.isConnected(hash.get(a), hash.get(b));
            }
        }
        return ans;
    }
}
