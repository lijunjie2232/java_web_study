USE `ssm2`;

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login`
(
    `id`       int(11)      NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL,
    `time`     datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

# insert multiple rows
INSERT INTO `login` (`username`)
VALUES ('admin'),
       ('user'),
       ('admin'),
       ('user'),
       ('admin'),
       ('user'),
       ('admin'),
       ('user');