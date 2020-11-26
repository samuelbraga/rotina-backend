ALTER TABLE `user`
ADD FOREIGN KEY (group_id) REFERENCES groups(id);