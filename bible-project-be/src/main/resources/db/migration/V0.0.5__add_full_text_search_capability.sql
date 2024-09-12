ALTER TABLE verses ADD COLUMN IF NOT EXISTS search_vector tsvector;

CREATE INDEX IF NOT EXISTS search_idx ON verses USING GIN(search_vector);

UPDATE verses SET search_vector = to_tsvector('english', text || ' ') where search_vector is NULL;
