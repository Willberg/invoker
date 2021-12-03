# **invoker**
学习算法和数据结构的项目, 提供java, go, ~~c, c++和python~~版, 记录了leetcode上常见的的题目和相关解答, 尽量提供详尽的注释进行解答.

## 算法
常见的练习题

#### 贪心
核心思想: 每次操作都是局部最优的，从而使最后得到的结果是全局最优的。

|题号|                                 题目名称                                   |                   代码                   |难度|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|
|11|[盛最多水的容器](https://leetcode-cn.com/problems/container-with-most-water/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/mostwater/Solution.java)| 中等 |
|55|[跳跃游戏](https://leetcode-cn.com/problems/jump-game/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/jumpgame/Solution.java)| 中等 |
|406|[根据身高重建队列](https://leetcode-cn.com/problems/queue-reconstruction-by-height/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/queuereconstruction/Solution.java)| 中等 |
|581|[最短无序连续子数组](https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/shortestunsorted/Solution.java)| 中等 |
|621|[任务调度器](https://leetcode-cn.com/problems/task-scheduler/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/taskscheduler/Solution.java)| 中等 |
|5923|[从房屋收集雨水需要的最少水桶数](https://leetcode-cn.com/problems/minimum-number-of-buckets-required-to-collect-rainwater-from-houses/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/minbuckets/Solution.java)| 中等 |
|5924|[从数组中移除最大网格图中机器人回家的最小代价值和最小值](https://leetcode-cn.com/problems/minimum-cost-homecoming-of-a-robot-in-a-grid/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/mincost/Solution.java)| 中等 |
|5940|[从数组中移除最大值和最小值](https://leetcode-cn.com/problems/removing-minimum-and-maximum-from-array/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/removingminimumandmaximumfromarray/Solution.java)| 中等 |
 
#### 滑动窗口

|题号|                                 题目名称                                   |                   代码                   |难度|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|
|5939|[半径为 k 的子数组平均值](https://leetcode-cn.com/problems/k-radius-subarray-averages/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/slidingwindow/kradiussubarrayaverages/Solution.java)| 中等 |

## 数据结构
常见的练习题

#### 数组

|题号|                                 题目名称                                   |                   代码                   |难度|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|
|5938|[找出数组排序后的目标下标](https://leetcode-cn.com/problems/find-target-indices-after-sorting-array/)|[Java](https://github.com/Willberg/invoker/blob/master/java/datastructure/array/dimension/findtargetindicesaftersortingarray/Solution.java)| 中等 |
#### 图
##### 最短路径
重点是迪杰斯特拉算法

|题号|                                 题目名称                                   |                   代码                   |难度|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|
|1514|[概率最大的路径](https://leetcode-cn.com/problems/path-with-maximum-probability/)|[Java](https://github.com/Willberg/invoker/blob/master/java/datastructure/graph/dijkstra/PathWithMaximumProbability.java)| 中等 |

##### 并查集
  
|题号|                                 题目名称                                   |                   代码                   |难度|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|
|399|[除法求值](https://leetcode-cn.com/problems/evaluate-division/)|[Java](https://github.com/Willberg/invoker/blob/master/java/datastructure/unionfind/evaluatedivision/Solution.java)| 中等 |

## 多线程
重点要掌握锁,信号量

|题号|                                 题目名称                                   |                   代码                   |难度|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|
|1114|[按序打印](https://leetcode-cn.com/problems/print-in-order/)|[Java](https://github.com/Willberg/invoker/blob/master/java/concurrency/printinorder/Foo.java)| 中等 |
|1115|[交替打印FooBar](https://leetcode-cn.com/problems/print-foobar-alternately/)|[Java](https://github.com/Willberg/invoker/tree/master/java/concurrency/printfoobaralternately) [Go](https://github.com/Willberg/invoker/blob/master/go/concurrency/print_zero_even_odd.go)| 中等 |
|1116|[打印零与奇偶数](https://leetcode-cn.com/problems/print-zero-even-odd/)|[Java](https://github.com/Willberg/invoker/blob/master/java/concurrency/printzeroevenodd/ZeroEvenOdd.java)| 中等 |
|1117|[H2O 生成](https://leetcode-cn.com/problems/building-h2o/)|[Java](https://github.com/Willberg/invoker/blob/master/java/concurrency/buildingh2o/H2O.java)| 中等 |
|1195|[交替打印字符串](https://leetcode-cn.com/problems/fizz-buzz-multithreaded/)|[Java](https://github.com/Willberg/invoker/blob/master/java/concurrency/fizzbuzzmultithreaded/FizzBuzz.java)| 中等 |
|1226|[哲学家进餐](https://leetcode-cn.com/problems/the-dining-philosophers/)|[Java](https://github.com/Willberg/invoker/blob/master/java/concurrency/thediningphilosophers/DiningPhilosophers.java)| 中等 |


## 数据库

|题号|                                 题目名称                                   |                   代码                   |难度|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|
|176|[第二高的薪水](https://leetcode-cn.com/problems/second-highest-salary/)|[sql](https://github.com/Willberg/invoker/blob/master/sql/second_highest_salary.sql)| 中等 |
|177|[第N高的薪水](https://leetcode-cn.com/problems/nth-highest-salary/)|[sql](https://github.com/Willberg/invoker/blob/master/sql/nth_highest_salary.sql)| 中等 |
|185|[部门工资前三高的所有员工](https://leetcode-cn.com/problems/department-top-three-salaries/)|[sql](https://github.com/Willberg/invoker/blob/master/sql/department_top_three_salaries.sql)| 中等 |
|596|[超过5名学生的课](https://leetcode-cn.com/problems/classes-more-than-5-students/)|[sql](https://github.com/Willberg/invoker/blob/master/sql/classes_more_than_5_students.sql)| 中等 |

## shell

|题号|                                 题目名称                                   |                   代码                   |难度|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|
|195|[第十行](https://leetcode-cn.com/problems/tenth-line/)|[bash](https://github.com/Willberg/invoker/blob/master/shell/tenth_line.sh)| 中等 |