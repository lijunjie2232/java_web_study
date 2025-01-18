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


-- 创建用户表
drop table if exists `user`;
create table `user`
(
    `id`       int(11)      not null auto_increment,
    `username` varchar(255) not null,
    `password` varchar(255) not null,
    `email`    varchar(255) default null,
    primary key (`id`)
);

-- 创建商品表
drop table if exists `goods`;
create table `goods`
(
    `id`          int(11)        not null auto_increment,
    `name`        varchar(255)   not null,
    `description` text    default null,
    `price`       decimal(10, 2) not null,
    `stock`       int(11) default 0,
    primary key (`id`)
);

-- 创建订单表
drop table if exists `order`;
create table `order`
(
    `id`         int(11)  not null auto_increment,
    `user_id`    int(11)  not null,
    `order_date` datetime not null default current_timestamp,
    primary key (`id`),
    foreign key (`user_id`) references `user` (`id`)
);


-- 创建订单项表
drop table if exists `order_item`;
create table `order_item`
(
    `id`       int(11)        not null auto_increment,
    `order_id` int(11)        not null,
    `goods_id` int(11)        not null,
    `quantity` int(11)        not null,
    `price`    decimal(10, 2) not null,
    primary key (`id`),
    foreign key (`order_id`) references `order` (`id`),
    foreign key (`goods_id`) references `goods` (`id`)
);


-- 插入用户数据
insert into `user` (`username`, `password`, `email`)
values ('user1', 'password1', 'user1@example.com'),
       ('user2', 'password2', 'user2@example.com');

-- 插入商品数据
insert into `goods` (`name`, `description`, `price`, `stock`)
values ('商品A', '这是商品A的描述', 100.00, 100),
       ('商品B', '这是商品B的描述', 200.00, 150),
       ('商品C', '这是商品C的描述', 300.00, 200);

-- 插入订单数据
insert into `order` (`user_id`, `order_date`)
values (1, '2023-10-01 10:00:00'),  -- 订单1
       (2, '2024-10-02 11:00:00');  -- 订单2

-- 插入订单项数据
insert into `order_item` (`order_id`, `goods_id`, `quantity`, `price`)
values (1, 1, 2, 100.00),  -- 订单1 包含 2 个 商品A
       (1, 2, 1, 200.00),  -- 订单1 包含 1 个 商品B
       (2, 2, 1, 200.00),  -- 订单2 包含 1 个 商品B
       (2, 3, 3, 300.00);  -- 订单2 包含 3 个 商品C

