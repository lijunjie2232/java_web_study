USE `ssm2`;

DROP TABLE IF EXISTS `log`;

CREATE TABLE `log`
(
    `id`       int(11)      NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL,
    `time`     datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

# insert multiple rows
INSERT INTO `log` (`username`)
VALUES ('admin'),
       ('user'),
       ('admin'),
       ('user'),
       ('admin'),
       ('admin'),
       ('user');