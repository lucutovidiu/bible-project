CREATE TABLE IF NOT EXISTS Books (
    id bigint PRIMARY KEY,
    name varchar(70) NOT NULL, -- ex: Genesa
    abbreviation varchar(20) NOT NULL, -- ex: Gen
    testament varchar(30) NOT NULL, -- 'Old Testament' or 'New Testament'
    created_at timestamp NOT NULL DEFAULT NOW(),
    updated_at timestamp
);

create sequence books_SEQ start with 1 increment by 50;
