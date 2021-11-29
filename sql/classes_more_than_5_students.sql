-- 596. 超过5名学生的课 https://leetcode-cn.com/problems/classes-more-than-5-students/
select class from courses
group by class
having count(distinct(student)) >= 5