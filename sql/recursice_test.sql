USE `test`;

-- CREATE TABLE company_department (
--     department_id INT PRIMARY KEY,
--     department_name VARCHAR(50),
--     parent_department_id INT REFERENCES company_department(department_id)
-- );
-- 
--  
-- INSERT INTO company_department 
--     (department_id, department_name, parent_department_id)
-- VALUES
--     (1, '公司', NULL),
--     (2, '人力资源部', 1),
--     (3, '财务部', 1),
--     (4, '市场部', 1),
--     (5, '技术部', 1),
--     (6, '招聘部', 2),
--     (7, '薪资部', 2),
--     (8, '成本控制部', 3),
--     (9, '收支管理部', 3),
--     (10, '品牌推广部', 4),
--     (11, '销售部', 4),
--     (12, '前端开发部', 5),
--     (13, '后端开发部', 5)


WITH RECURSIVE department_tree (department_id, department_name, parent_department_id, depth, path) AS (
		SELECT 
			department_id, 
			department_name, 
			parent_department_id, 
			1 AS depth, 
			CAST(department_id AS CHAR(200)) AS path
		FROM company_department
		WHERE parent_department_id IS NULL
		UNION ALL
		SELECT 
			cd.department_id, 
			cd.department_name, 
			cd.parent_department_id, 
			dt.depth + 1 AS depth, 
			CONCAT(dt.path, ',', cd.department_id) AS path
		FROM company_department cd
			JOIN department_tree dt ON cd.parent_department_id = dt.department_id
	)
SELECT 
	department_id, department_name, parent_department_id, depth, path
FROM department_tree
ORDER BY path;
