CREATE TABLE IF NOT EXISTS `profile` (
    `id` BINARY(16) NOT NULL PRIMARY KEY,
    `type` varchar(255) NOT NULL UNIQUE
) ENGINE = innodb;