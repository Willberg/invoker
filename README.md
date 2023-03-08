# **invoker**
学习算法和数据结构的项目, 提供java, go, c, ~~c++和python~~版, 记录了leetcode上常见的的题目和相关解答, 尽量提供详尽的注释进行解答. HOT100最好全部做一遍, 出现在周赛里的会特别标出, 其他按星级排名, 星越多越重要.

## 算法
常见的练习题, 包括贪心算法, 双指针, 二分查找, 排序, 优先搜索, 动态规划, 分治法, 数学, 位运算.
- 双指针: 基本双指针, 快慢指针, 滑动窗口
- 优先搜索: 深度优先, 回溯, 广度优先
- 排序: 
    - 比较排序: 冒泡排序, 插入排序, 选择排序, 希尔排序, 快速排序, 堆排序, 归并排序
    - 非比较排序: 计数排序, 桶排序, 基数排序
- 动态规划: 一维, 二维, 分割类型题, 子序列问题, 背包问题, 字符串编辑, 股票交易
- 数学: 公倍数与公因数, 质数, 数字处理, 随机与采样

### 贪心
核心思想: 每次操作都是局部最优的，从而使最后得到的结果是全局最优的。

|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|
|11|[盛最多水的容器](https://leetcode-cn.com/problems/container-with-most-water/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/mostwater/Solution.java)| 中等 | HOT100 |
|55|[跳跃游戏](https://leetcode-cn.com/problems/jump-game/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/jumpgame/Solution.java)| 中等 | HOT100 |
|406|[根据身高重建队列](https://leetcode-cn.com/problems/queue-reconstruction-by-height/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/queuereconstruction/Solution.java)| 中等 | HOT100 |
|581|[最短无序连续子数组](https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/shortestunsorted/Solution.java)| 中等 | HOT100 |
|621|[任务调度器](https://leetcode-cn.com/problems/task-scheduler/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/taskscheduler/Solution.java)| 中等 | HOT100 |
|921|[使括号有效的最少添加](https://leetcode.cn/problems/minimum-add-to-make-parentheses-valid/)|[Go](https://github.com/Willberg/invoker/blob/master/golang/algorithm/greedy/minadd/minadd.go) [C](https://github.com/Willberg/invoker/blob/master/c/algorithm/greedy/minadd/minadd.c)| 中等 | ⭐️ |
|2086|[从房屋收集雨水需要的最少水桶数](https://leetcode-cn.com/problems/minimum-number-of-buckets-required-to-collect-rainwater-from-houses/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/minbuckets/Solution.java)| 中等 | 第 66 场双周赛 |
|2087|[从数组中移除最大网格图中机器人回家的最小代价值和最小值](https://leetcode-cn.com/problems/minimum-cost-homecoming-of-a-robot-in-a-grid/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/mincost/Solution.java)| 中等 |第 66 场双周赛|
|2091|[从数组中移除最大值和最小值](https://leetcode-cn.com/problems/removing-minimum-and-maximum-from-array/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/greedy/removingminimumandmaximumfromarray/Solution.java)| 中等 |第 269 场周赛|

### 双指针
#### 基本双指针
|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|

#### 快慢指针
|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|

#### 滑动窗口
|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|
|2090|[半径为 k 的子数组平均值](https://leetcode-cn.com/problems/k-radius-subarray-averages/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/slidingwindow/kradiussubarrayaverages/Solution.java)| 中等 | 第269场周赛 |

### 二分查找
二分查找也常被称为二分法或者折半查找，每次查找时通过将待查找区间分成两部分并只取一部分继续查找，将查找的复杂度大大减少。对于一个长度为 O(n)的数组，二分查找的时间复杂度为 O(log n)。
|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|
|4|[寻找两个正序数组的中位数](https://leetcode-cn.com/problems/median-of-two-sorted-arrays/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/binarysearch/median/Solution.java)| 困难 | HOT100 |
|33|[搜索旋转排序数组](https://leetcode-cn.com/problems/search-in-rotated-sorted-array/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/binarysearch/rotatedarray/Solution.java)| 中等 | HOT100 |
|34|[在排序数组中查找元素的第一个和最后一个位置](https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/binarysearch/findposition/Solution.java)| 中等 | HOT100 |
|240|[搜索二维矩阵II](https://leetcode-cn.com/problems/search-a-2d-matrix-ii/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/binarysearch/searchmatrix/Solution.java)| 中等 | HOT100 |
|287|[寻找重复数](https://leetcode-cn.com/problems/find-the-duplicate-number/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/binarysearch/findduplicate/Solution.java)| 中等 | HOT100 |
|300|[最长递增子序列](https://leetcode-cn.com/problems/longest-increasing-subsequence/)|[Java](https://github.com/Willberg/invoker/blob/master/java/algorithm/binarysearch/longestincreasingsubsequence/Solution.java)| 中等 | HOT100 |

## 数据结构
常见的练习题, 包括数组, 字符串, 栈, 队列, 哈希表, 多重集合与映射, 前缀和与积分图, 链表, 树, 图, 复杂数据结构.
- 栈: 基本栈, 单调栈
- 队列: 优先队列, 双端队列
- 树: 二叉树, 二叉搜索树, 字典树
- 复杂数据结构: LRU, 并查集

#### 数组

|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|
|2089|[找出数组排序后的目标下标](https://leetcode-cn.com/problems/find-target-indices-after-sorting-array/)|[Java](https://github.com/Willberg/invoker/blob/master/java/datastructure/array/dimension/findtargetindicesaftersortingarray/Solution.java)| 中等 | 第269场周赛 |

#### 队列
##### 优先队列
|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|
||设计通用版优先队列|[Java](https://github.com/Willberg/invoker/blob/master/java/datastructure/queue/priorityqueue/PriorityQueue.java) [Go](https://github.com/Willberg/invoker/blob/master/golang/datastructure/queue/priorityqueue/priorityqueue.go) [C](https://github.com/Willberg/invoker/blob/master/c/datastructure/queue/priorityqueue/priorityqueue.c)| 困难 | ⭐️⭐️⭐️⭐️ |
|23|[合并K个升序链表](https://leetcode.cn/problems/merge-k-sorted-lists/)|[Java](https://github.com/Willberg/invoker/blob/master/java/datastructure/queue/priorityqueue/TestPriorityQueue.java) [Go](https://github.com/Willberg/invoker/blob/master/golang/datastructure/queue/mergeklists/mergeklists.go) [C](https://github.com/Willberg/invoker/blob/master/c/datastructure/queue/test/mergeklist/test_mergeklist.c)| 困难 | ⭐️⭐️⭐️⭐️ |

#### 链表
|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|
|707|[设计链表](https://leetcode.cn/problems/design-linked-list/)|[Go](https://github.com/Willberg/invoker/blob/master/golang/datastructure/list/linkedlist/linkedlist.go) [C](https://github.com/Willberg/invoker/blob/master/c/datastructure/list/linkedlist/linkedlist.c)| 中等 | ⭐️⭐️⭐️ |
|4|[用C语言实现malloc和free函数](https://en.wikipedia.org/wiki/C_dynamic_memory_allocation)|[C](https://github.com/Willberg/invoker/blob/master/c/datastructure/list/mymalloc/mymalloc.c)| 困难 | ⭐️⭐️⭐️⭐️ |

#### 哈希
|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|
|817|[链表组件](https://leetcode.cn/problems/linked-list-components/)|[Go](https://github.com/Willberg/invoker/blob/master/golang/datastructure/hashtable/linklistcomponents/linklistcomponents.go) [C](https://github.com/Willberg/invoker/blob/master/c/datastructure/hashtable/listcomponents/listcomponents.c)| 中等 | ⭐️ |


#### 图
##### 最短路径
重点是迪杰斯特拉算法

|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|
|1514|[概率最大的路径](https://leetcode-cn.com/problems/path-with-maximum-probability/)|[Java](https://github.com/Willberg/invoker/blob/master/java/datastructure/graph/dijkstra/PathWithMaximumProbability.java)| 中等 | ⭐️⭐️⭐️ |

#### 复杂数据结构
##### 并查集
  
|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|
|399|[除法求值](https://leetcode-cn.com/problems/evaluate-division/)|[Go](https://github.com/Willberg/invoker/blob/master/golang/datastructure/unionfind/evaluatedivision/ed.go) [Java](https://github.com/Willberg/invoker/blob/master/java/datastructure/unionfind/evaluatedivision/Solution.java)| 中等 | HOT100 |

##### 跳表
|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|
|1206|[设计跳表](https://leetcode.cn/problems/design-skiplist/)|[C](https://github.com/Willberg/invoker/blob/2392b46e86/c/datastructure/list/skiplist/skiplist.c) [Go](https://github.com/Willberg/invoker/blob/master/golang/datastructure/list/skiplist/skiplist.go) [C++](https://github.com/Willberg/invoker/blob/master/cpp/datastructure/list/skiplist/skiplist.h)| 困难 | ⭐️⭐️⭐️⭐️ |

##### 平衡二叉搜索树
|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|
|2|[设计平衡二叉搜索树](https://zh.wikipedia.org/wiki/AVL%E6%A0%91)|[C](https://github.com/Willberg/invoker/blob/master/c/datastructure/tree/avl/avl.c) [Go](https://github.com/Willberg/invoker/blob/master/golang/datastructure/tree/avl/avl.go) | 困难 | ⭐️⭐️⭐️⭐️ |
|3|[设计红黑树](https://zh.wikipedia.org/wiki/%E7%BA%A2%E9%BB%91%E6%A0%91)|[C++](https://github.com/Willberg/invoker/blob/master/cpp/datastructure/tree/redblacktree/redblacktree.cpp) [Go]() | 困难 | ⭐️⭐️⭐️⭐️ |


## 多线程
重点要掌握锁,信号量

|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|
|1114|[按序打印](https://leetcode-cn.com/problems/print-in-order/)|[Java](https://github.com/Willberg/invoker/blob/master/java/concurrency/printinorder/Foo.java)| 中等 |  ⭐️⭐️⭐️  |
|1115|[交替打印FooBar](https://leetcode-cn.com/problems/print-foobar-alternately/)|[Java](https://github.com/Willberg/invoker/tree/master/java/concurrency/printfoobaralternately)| 中等 |  ⭐️⭐️⭐️  |
|1116|[打印零与奇偶数](https://leetcode-cn.com/problems/print-zero-even-odd/)|[Java](https://github.com/Willberg/invoker/blob/master/java/concurrency/printzeroevenodd/ZeroEvenOdd.java) [Go](https://github.com/Willberg/invoker/blob/master/golang/concurrency/print_zero_even_odd/print_zero_even_odd.go)| 中等 |  ⭐️⭐️⭐️  |
|1117|[H2O 生成](https://leetcode-cn.com/problems/building-h2o/)|[Java](https://github.com/Willberg/invoker/blob/master/java/concurrency/buildingh2o/H2O.java)| 中等 |  ⭐️⭐️⭐️  |
|1195|[交替打印字符串](https://leetcode-cn.com/problems/fizz-buzz-multithreaded/)|[Java](https://github.com/Willberg/invoker/blob/master/java/concurrency/fizzbuzzmultithreaded/FizzBuzz.java)| 中等 |  ⭐️⭐️⭐️  |
|1226|[哲学家进餐](https://leetcode-cn.com/problems/the-dining-philosophers/)|[Java](https://github.com/Willberg/invoker/blob/master/java/concurrency/thediningphilosophers/DiningPhilosophers.java)| 中等 |  ⭐️⭐️⭐️  |
||读写锁|[Java](https://github.com/Willberg/invoker/blob/master/java/concurrency/lock/impl/RwLockImpl.java) [Go](https://github.com/Willberg/invoker/blob/master/golang/concurrency/lock/rwlock/rwlock.go)| 中等 |  ⭐️⭐️⭐️  |


## 数据库

|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|
|176|[第二高的薪水](https://leetcode-cn.com/problems/second-highest-salary/)|[sql](https://github.com/Willberg/invoker/blob/master/sql/second_highest_salary.sql)| 中等 |  ⭐️⭐️⭐️  |
|177|[第N高的薪水](https://leetcode-cn.com/problems/nth-highest-salary/)|[sql](https://github.com/Willberg/invoker/blob/master/sql/nth_highest_salary.sql)| 中等 |  ⭐️⭐️⭐️  |
|185|[部门工资前三高的所有员工](https://leetcode-cn.com/problems/department-top-three-salaries/)|[sql](https://github.com/Willberg/invoker/blob/master/sql/department_top_three_salaries.sql)| 中等 |  ⭐️⭐️⭐️  |
|596|[超过5名学生的课](https://leetcode-cn.com/problems/classes-more-than-5-students/)|[sql](https://github.com/Willberg/invoker/blob/master/sql/classes_more_than_5_students.sql)| 中等 |  ⭐️⭐️⭐️  |

## 分布式
|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|
||一致性hash|[Java](https://github.com/Willberg/invoker/blob/master/java/distributed/consistenthash/ConsistentHash.java)| 中等 |  ⭐️⭐️⭐️  |
||分布式自增ID|[Java](https://github.com/Willberg/invoker/blob/master/java/distributed/selfIncID/SelfIncID.java)| 中等 |  ⭐️⭐️⭐️  |

## shell

|题号|                                 题目名称                                   |                   代码                   |难度|级别|
|:--:|:------------------------------------------------------------------------:|:---------------------------------------:|:--:|:--:|
|195|[第十行](https://leetcode-cn.com/problems/tenth-line/)|[bash](https://github.com/Willberg/invoker/blob/master/shell/tenth_line.sh)| 中等 |  ⭐️⭐️⭐️  |