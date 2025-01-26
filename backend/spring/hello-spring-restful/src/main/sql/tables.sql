USE `ssm2`;

DROP TABLE IF EXISTS `employee`;
# create table employee
CREATE TABLE IF NOT EXISTS `employee`
(
    `id`      INT(11)        NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(255)   NOT NULL,
    `age`     INT(11)        NOT NULL,
    `email`   VARCHAR(255)   NOT NULL,
    `gender`  INT1           NOT NULL,
    `address` VARCHAR(255)   NOT NULL,
    `salary`  DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
);

# insert data
INSERT INTO `employee` (`id`, `name`, `age`, `email`, `gender`, `address`, `salary`)
VALUES (1, 'John', 30, 'john@example.com', 1, 'New York', 5000.00),
       (2, 'Jane', 28, 'jane@example.com', 0, 'London', 4000.00),
       (3, 'Jim', 35, 'jim@example.com', 1, 'Tokyo', 6000.00),
       (4, 'Judy', 27, 'judy@example.com', 0, 'Shanghai', 4500.00),
       (5, 'Joe', 32, 'joe@example.com', 1, 'Beijing', 5500.00);

# select
SELECT *
FROM employee;