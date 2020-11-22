ALTER TABLE `company`
ADD `image_id` BINARY(16);

ALTER TABLE `company`
ADD FOREIGN KEY (image_id) REFERENCES image(id);