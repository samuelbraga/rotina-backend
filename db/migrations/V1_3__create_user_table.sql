CREATE TABLE IF NOT EXISTS `user` (
    `id` BINARY(16) NOT NULL PRIMARY KEY,
    `email` varchar(255) NOT NULL UNIQUE,
    `name` varchar(255) NOT NULL,
    `last_name` varchar(255) NOT NULL,
    `phone` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `company_id` BINARY(16) NOT NULL,
    FOREIGN KEY (company_id) REFERENCES company(id)
) ENGINE = innodb;