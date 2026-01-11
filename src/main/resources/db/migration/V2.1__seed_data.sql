-- ============================
-- Seed data for Cozy Cuppa
-- ============================

-- Tags (insert only if not already present)
INSERT INTO tags (id, name)
SELECT uuid_generate_v4(), x.name
FROM (VALUES
          ('cozy'),
          ('fantasy'),
          ('baking'),
          ('reviews')
     ) AS x(name)
WHERE NOT EXISTS (
    SELECT 1 FROM tags t WHERE t.name = x.name
);

-- Post (insert only if not already present)
INSERT INTO posts (
    id,
    slug,
    title,
    summary,
    content,
    type,
    cover_image_url,
    status,
    published_at
)
SELECT
    uuid_generate_v4(),
    'welcome-to-the-cozy-cuppa',
    'Welcome to The Cozy Cuppa',
    'A warm place for cozy fantasy, baking, and gentle conversation.',
    'This is the very first post on The Cozy Cuppa. Settle in, grab a warm drink, and enjoy.',
    'ESSAY',
    NULL,
    'PUBLISHED',
    now()
    WHERE NOT EXISTS (
  SELECT 1 FROM posts p WHERE p.slug = 'welcome-to-the-cozy-cuppa'
);

-- Link post ↔ tags
INSERT INTO post_tags (post_id, tag_id)
SELECT p.id, t.id
FROM posts p
         JOIN tags t ON t.name IN ('cozy', 'fantasy')
WHERE p.slug = 'welcome-to-the-cozy-cuppa'
    ON CONFLICT DO NOTHING;
