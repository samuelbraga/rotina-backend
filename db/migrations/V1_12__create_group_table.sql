CREATE TABLE IF NOT EXISTS `groups` (
    `id` BINARY(16) NOT NULL PRIMARY KEY,
    `company_id` BINARY(16) NOT NULL,
    `name` varchar(255) NOT NULL,
    FOREIGN KEY (company_id) REFERENCES company(id)
) ENGINE = innodb;