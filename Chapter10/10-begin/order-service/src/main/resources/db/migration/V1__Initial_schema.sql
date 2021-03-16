CREATE TABLE orders (
      id                  BIGSERIAL PRIMARY KEY NOT NULL,
      created_date        bigint NOT NULL,
      last_modified_date  bigint NOT NULL,
      version             integer NOT NULL,
      book_isbn           varchar(255) NOT NULL,
      book_name           varchar(255),
      book_price          float8,
      quantity            int NOT NULL,
      status              varchar(255) NOT NULL
);