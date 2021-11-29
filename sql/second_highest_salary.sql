-- 176. 第二高的薪水 https://leetcode-cn.com/problems/second-highest-salary/
select (select distinct(Salary) from Employee order by Salary desc limit 1,1) as SecondHighestSalary