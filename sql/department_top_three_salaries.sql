-- 185. 部门工资前三高的所有员工 https://leetcode-cn.com/problems/department-top-three-salaries/
select d1.Name as Department, e1.Name as Employee, e1.Salary from Employee e1 
left join Department d1 on d1.Id = e1.DepartmentId
where 3 > (select count(distinct(e2.Salary)) from Employee e2 where e1.DepartmentId = e2.DepartmentId and e1.Salary < e2.Salary)
order by e1.Id asc, e1.Salary desc;