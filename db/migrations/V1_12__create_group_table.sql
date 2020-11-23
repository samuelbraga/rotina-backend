CREATE TABLE IF NOT EXISTS `group` (
    `id` BINARY(16) NOT NULL PRIMARY KEY,
    `company_id` BINARY(16) NOT NULL,
    `name` varchar(255) NOT NULL UNIQUE,
    FOREIGN KEY (company_id) REFERENCES company(id)
) ENGINE = innodb;