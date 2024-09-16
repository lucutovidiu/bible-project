CREATE INDEX IF NOT EXISTS idx_book_name ON Books(name);

CREATE INDEX IF NOT EXISTS idx_chapter_number ON Chapters(book_id, chapter_number);

CREATE INDEX IF NOT EXISTS idx_verse_number ON Verses(chapter_id, verse_number);

CREATE INDEX IF NOT EXISTS search_idx ON verses USING GIN (search_vector);
