//240. 搜索二维矩阵 II https://leetcode-cn.com/problems/search-a-2d-matrix-ii/
package algorithm.binarysearch.searchmatrix;

// 从矩阵右上角开始,比较当前值与target的关系,大了左移,小了下移,这样可以扫描整个矩阵;如果从左上角开始只能扫描对角线的一角
public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int m = matrix.length, n = matrix[0].length;
        int i = 0, j = n - 1;
        while (i < m && j >= 0) {
            if (target == matrix[i][j]) {
                return true;
            } else if (target < matrix[i][j]) {
                j--;
            } else {
                i++;
            }
        }
        return false;
    }
}
