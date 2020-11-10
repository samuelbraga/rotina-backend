INSERT INTO user_profiles (
    user_id,
    profiles_id
) VALUES (
    (SELECT id FROM user WHERE email = "samuel.fantini@fantinisa.com"),
    (SELECT id FROM profile WHERE type = "SUPER_ADMIN")
);