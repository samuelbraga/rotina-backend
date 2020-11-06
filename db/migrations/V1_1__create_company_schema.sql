CREATE TABLE IF NOT EXISTS `company` (
    `id` BINARY(16) NOT NULL PRIMARY KEY,
    `name` varchar(255) NOT NULL UNIQUE
) ENGINE = innodb;