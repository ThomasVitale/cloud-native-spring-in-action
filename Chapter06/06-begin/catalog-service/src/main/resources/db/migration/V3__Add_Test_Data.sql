ALTER TABLE book
ALTER COLUMN created_date SET DEFAULT current_timestamp;

ALTER TABLE book
ALTER COLUMN last_modified_date SET DEFAULT current_timestamp;

INSERT INTO book(author, isbn, price, title, version)
VALUES ('LANLAN', '1234554321', 132.1, 'GREAT LANLAN', 1);

INSERT INTO book(author, isbn, price, title, version)
VALUES ('LANLAN', '1234567890', 132.1, 'BEST LANLAN', 1);