CREATE TABLE IF NOT EXISTS `image` (
    `id` BINARY(16) NOT NULL PRIMARY KEY,
    `image_url` varchar(255) NOT NULL
) ENGINE = innodb;