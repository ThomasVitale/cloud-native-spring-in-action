create index book__index_createdDate
    on public.book (created_date);

create index book__index_lastUpdatedDate
    on public.book (last_modified_date);

create index book__index_title
    on public.book (title);