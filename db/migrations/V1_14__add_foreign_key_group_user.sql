ALTER TABLE `user`
ADD FOREIGN KEY (group_id) REFERENCES `group`(id);