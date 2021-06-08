create table book_genres(
    book_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,

    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (genre_id) REFERENCES genres(id),
    UNIQUE (book_id, genre_id)
);