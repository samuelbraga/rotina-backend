CREATE TABLE IF NOT EXISTS `user_profiles` (
    `user_id` BINARY(16) NOT NULL,
    `profiles_id` BINARY(16) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (profiles_id) REFERENCES profile(id),
    INDEX(user_id, profiles_id)
) ENGINE = innodb;