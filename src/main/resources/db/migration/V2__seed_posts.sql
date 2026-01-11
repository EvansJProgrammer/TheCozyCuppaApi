INSERT INTO tags (id, name)
VALUES
    (uuid_generate_v4(), 'cozy'),
    (uuid_generate_v4(), 'fantasy'),
    (uuid_generate_v4(), 'baking');

-- Post
INSERT INTO posts (
    id,
    slug,
    title,
    summary,
    content,
    type,
    status,
    published_at
)
VALUES (
           uuid_generate_v4(),
           'welcome-to-the-cozy-cuppa',
           'Welcome to The Cozy Cuppa',
           'A warm place to share cozy fantasy, baking, and quiet conversations.',
           'This is the very first post on The Cozy Cuppa. Settle in, grab a warm drink, and enjoy.',
           'ESSAY',
           'PUBLISHED',
           now()
       );

-- Post ↔ Tags
INSERT INTO post_tags (post_id, tag_id)
SELECT p.id, t.id
FROM posts p
         JOIN tags t ON t.name IN ('cozy', 'fantasy')
WHERE p.slug = 'welcome-to-the-cozy-cuppa';
