    INSERT INTO users (email, username, password, role)
SELECT * FROM (SELECT 'admin@gmail.com', 'admin', '$2a$10$QbGBeIvW47RsEu6b4oE.RuyzSMVuM3iuT5JklMH5iNqIUCz4/3LFm', 0) AS tmp
WHERE NOT EXISTS (
    SELECT username FROM users WHERE username = 'admin'
) LIMIT 1;
