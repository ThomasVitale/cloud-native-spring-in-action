ALTER TABLE orders
    ADD COLUMN created_by varchar(255);

ALTER TABLE orders
    ADD COLUMN last_modified_by varchar(255);