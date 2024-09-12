CREATE TABLE IF NOT EXISTS Chapters (
    id bigint PRIMARY KEY,
    book_id bigint NOT NULL,
    chapter_number INTEGER NOT NULL,
    FOREIGN KEY (book_id) REFERENCES Books(id) ON DELETE CASCADE,
    created_at timestamp NOT NULL DEFAULT NOW(),
    updated_at timestamp
);

create sequence chapters_SEQ start with 1 increment by 50;
