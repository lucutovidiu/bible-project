CREATE TABLE IF NOT EXISTS Books (
    id bigint PRIMARY KEY,
    book_order int DEFAULT floor(random() * 1000),
    name varchar(70) NOT NULL, -- ex: Genesa
    abbreviation varchar(20) NOT NULL, -- ex: Gen
    testament varchar(30) NOT NULL, -- 'Old Testament' or 'New Testament'
    requires_update boolean default false,
    in_progress boolean default false,
    downloaded_link varchar(150),
    exp_chapters_count int default 0,
    exp_total_verses int default 0,
    created_at timestamp NOT NULL DEFAULT NOW(),
    updated_at timestamp NOT NULL DEFAULT NOW()
);

create sequence books_SEQ start with 1 increment by 50;
