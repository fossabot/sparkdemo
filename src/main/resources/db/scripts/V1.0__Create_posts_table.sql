CREATE TABLE posts (
    uuid uuid primary key,
    summary text,
    title text not null,
    content text not null,
    author VARCHAR(50) not null,
    tags VARCHAR(200),
    categories VARCHAR(200),
    publication_status int,
    created_At date,
    updated_At date,
);