/*create database Merchandise;*//*创建数据库*/

DROP TABLE IF EXISTS `user`;
create table `user`
(
`id` bigint unsigned not null auto_increment primary key,
`username` varchar(50) not null,
`sign` bigint not null,
`email` varchar(100) not null,
`password` varchar(1024) not null,
`roles` varchar(100) not null
)charset utf8;

DROP TABLE IF EXISTS `offer`;
create table `offer`
(
`id` bigint unsigned not null auto_increment primary key,
`user_id` bigint unsigned not null,
`pet` varchar(100) not null,
`total` int not null,
`rest` int not null,
`price` double not null,
`place_timestamp` bigint unsigned  not null,
CONSTRAINT `offer_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
INDEX `index_user`(`user_id`)
)charset utf8;

DROP TABLE IF EXISTS `deal`;
create table `deal`
(
`id` bigint unsigned not null auto_increment primary key,
`offer_id` bigint unsigned not null,
`buyer_id` bigint unsigned not null,
`quantity` int not null,
`cost` double not null,
`place_timestamp` bigint unsigned  not null,
`status` int unsigned not null,
CONSTRAINT `deal_offer_fk` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`),
CONSTRAINT `deal_buyer_fk` FOREIGN KEY (`buyer_id`) REFERENCES `user` (`id`),
INDEX `index_user`(`buyer_id`),
INDEX `index_offer`(`offer_id`)
)charset utf8;

