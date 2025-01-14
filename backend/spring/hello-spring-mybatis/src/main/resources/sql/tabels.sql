use `ssm3`;

drop table if exists `emp`;

create table `emp`
(
    `id`     int(11)        not null auto_increment,
    `name`   varchar(255)   not null,
    `age`    int(11)        not null,
    `salary` decimal(10, 2) not null,
    primary key (`id`)
);

insert into `emp` (`name`, `age`, `salary`)
values ('张三', 18, 10000.00),
       ('李四', 19, 20000.00),
       ('王五', 20, 30000.00),
       ('赵六', 21, 40000.00),
       ('钱七', 22, 50000.00);
;