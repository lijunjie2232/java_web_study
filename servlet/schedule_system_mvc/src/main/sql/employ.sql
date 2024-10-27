USE `webtest`;
SET NAMES utf8mb4;

DROP TABLE IF EXISTS `t_emp`
CREATE TABLE IF NOT EXISTS `t_emp`
(
    emp_id     INT AUTO_INCREMENT COMMENT 'employ id' PRIMARY KEY,
    emp_name   VARCHAR(100)  NOT NULL COMMENT 'employ name',
    emp_age    INT           NOT NULL COMMENT 'employ age',
    emp_salary DOUBLE(10, 5) NOT NULL COMMENT 'employ salary'
);

INSERT INTO `t_emp` (`emp_name`, `emp_salary`, `emp_age`)
VALUES ('zhao', 100.00, 20),
       ('qian', 200.00, 22),
       ('shun', 700.765, 40);

SELECT VERSION();