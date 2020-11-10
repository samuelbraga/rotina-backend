INSERT INTO user (
    id,
    email,
    name,
    last_name,
    phone,
    password,
    company_id
) VALUES (
    unhex(replace(uuid(),'-','')),
    "samuel.fantini@fantinisa.com",
    "Samuel",
    "Fantini",
    "31970707070",
    "$2a$10$UfxW52RLlyp4FnopD9hJbeEYLw3HVlL7lmFsF4JeA/yKUINQwZyrm",
    (SELECT id FROM company WHERE name = "Fantini S.A.")
);