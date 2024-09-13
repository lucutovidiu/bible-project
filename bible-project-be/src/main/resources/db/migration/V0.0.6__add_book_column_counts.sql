ALTER TABLE Books ADD exp_chapters_count int;

ALTER TABLE Books ADD exp_total_verses int;

ALTER TABLE Books ADD downloaded_link varchar(150);

ALTER TABLE Books ADD requires_update boolean default false;
