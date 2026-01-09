-- ============================
-- Cozy Cuppa - Initial Schema
-- ============================

-- Enable UUID generation
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ============================
-- Posts
-- ============================
CREATE TABLE posts (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

                       slug TEXT NOT NULL UNIQUE,
                       title TEXT NOT NULL,
                       summary TEXT,
                       content TEXT NOT NULL,

                       type TEXT NOT NULL, -- REVIEW | RECIPE | ESSAY

                       cover_image_url TEXT,

                       status TEXT NOT NULL DEFAULT 'DRAFT', -- DRAFT | PUBLISHED

                       published_at TIMESTAMP WITH TIME ZONE,
                       created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
                       updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE INDEX idx_posts_type ON posts(type);
CREATE INDEX idx_posts_status ON posts(status);
CREATE INDEX idx_posts_published_at ON posts(published_at);

-- ============================
-- Tags
-- ============================
CREATE TABLE tags (
                      id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                      name TEXT NOT NULL UNIQUE
);

-- ============================
-- Post ↔ Tags (Many-to-Many)
-- ============================
CREATE TABLE post_tags (
                           post_id UUID NOT NULL REFERENCES posts(id) ON DELETE CASCADE,
                           tag_id UUID NOT NULL REFERENCES tags(id) ON DELETE CASCADE,
                           PRIMARY KEY (post_id, tag_id)
);

CREATE INDEX idx_post_tags_post ON post_tags(post_id);
CREATE INDEX idx_post_tags_tag ON post_tags(tag_id);

-- ============================
-- Auto-update updated_at
-- ============================
CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_posts_updated
    BEFORE UPDATE ON posts
    FOR EACH ROW
    EXECUTE FUNCTION set_updated_at();
