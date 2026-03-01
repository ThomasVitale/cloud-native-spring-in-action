CREATE TABLE book (
                      id      BIGSERIAL PRIMARY KEY NOT NULL,
                      isbn    varchar(255) UNIQUE NOT NULL,
                      title   varchar(255) NOT NULL,
                      author  varchar(255) NOT NULL,
                      price   float8 NOT NULL,
                      created_date        timestamp NOT NULL DEFAULT current_timestamp,
                      last_modified_date  timestamp NOT NULL DEFAULT current_timestamp,
                      version integer NOT NULL DEFAULT 1
);

INSERT INTO book(isbn, title, author, price, version) VALUES ('1234567890', 'GOOD GUY', 'LANLAN', 12.2, 1);
INSERT INTO book(isbn, title, author, price, version) VALUES ('0123456789', 'BAD GUY', 'LANLAN', 22.2, 1);