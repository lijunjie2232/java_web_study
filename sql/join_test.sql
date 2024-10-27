use test;
-- join == inner join
SELECT * FROM a JOIN b ON a.name = b.name;
SELECT * FROM a INNER JOIN b ON a.name = b.name;

-- left join
SELECT * FROM a LEFT JOIN b ON a.name = b.name;
SELECT * FROM a LEFT JOIN b ON a.name = b.name WHERE b.name IS NULL;

-- right join
SELECT * FROM a RIGHT JOIN b ON a.name = b.name;
SELECT * FROM a RIGHT JOIN b ON a.name = b.name WHERE a.name IS NULL;

-- outer join
SELECT * FROM (
SELECT a.name, age, income FROM a LEFT JOIN b ON a.name = b.name
UNION
SELECT b.name, age, income  FROM a RIGHT JOIN b ON a.name = b.name) c;

-- cross join
SELECT * FROM a
CROSS JOIN b
WHERE a.age > 80 and b.income > 400;

SELECT * from a,b WHERE a.age > 80 and b.income > 400;
