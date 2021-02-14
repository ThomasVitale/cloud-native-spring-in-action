CREATE TABLE book (
      id                  BIGSERIAL PRIMARY KEY NOT NULL,
      created_date        bigint NOT NULL,
      last_modified_date  bigint NOT NULL,
      version             integer NOT NULL,
      author              varchar(255) NOT NULL,
      isbn                varchar(255) UNIQUE NOT NULL,
      price               float8 NOT NULL,
      publishing_year     integer,
      title               varchar(255) NOT NULL
);