-- 177. 第N高的薪水 https://leetcode-cn.com/problems/nth-highest-salary/
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
  SET N = N-1;
  RETURN (
      # Write your MySQL query statement below.
      select distinct(salary) from employee order by salary desc limit N, 1
  );
END