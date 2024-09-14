CREATE TABLE IF NOT EXISTS Verses (
    id bigint PRIMARY KEY,
    chapter_id bigint NOT NULL,
    verse_number INTEGER NOT NULL,
    text_diacritics TEXT,
    text TEXT NOT NULL,
    search_vector tsvector,
    FOREIGN KEY (chapter_id) REFERENCES Chapters(id) ON DELETE CASCADE,
    created_at timestamp NOT NULL DEFAULT NOW(),
    updated_at timestamp NOT NULL DEFAULT NOW()
);

create sequence verses_SEQ start with 1 increment by 50;
