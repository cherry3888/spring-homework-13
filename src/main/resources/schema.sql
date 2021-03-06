DROP TABLE IF EXISTS AUTHOR;
CREATE TABLE AUTHOR(
        ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        NAME VARCHAR(255) NOT NULL,
        CONSTRAINT AUTHOR_UNIQ UNIQUE (NAME)
    );
COMMENT ON TABLE AUTHOR IS 'Авторы';
COMMENT ON COLUMN AUTHOR.ID IS 'Идентификатор';
COMMENT ON COLUMN AUTHOR.NAME IS 'Ф.И.О. автора';


DROP TABLE IF EXISTS GENRE;
CREATE TABLE GENRE(
        ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        NAME VARCHAR(255) NOT NULL,
        CONSTRAINT GENRE_UNIQ UNIQUE (NAME)
    );
COMMENT ON TABLE GENRE IS 'Авторы';
COMMENT ON COLUMN GENRE.ID IS 'Идентификатор';
COMMENT ON COLUMN GENRE.NAME IS 'Наименование';


DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK(
        ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        TITLE VARCHAR(255) NOT NULL,
        AUTHOR_ID BIGINT NOT NULL,
        GENRE_ID BIGINT NOT NULL,
        FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHOR (ID) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (GENRE_ID) REFERENCES GENRE (ID) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT BOOK_UNIQ UNIQUE (TITLE, AUTHOR_ID, GENRE_ID)
    );
COMMENT ON TABLE BOOK IS 'Книги';
COMMENT ON COLUMN BOOK.ID IS 'Идентификатор';
COMMENT ON COLUMN BOOK.TITLE IS 'Название';
COMMENT ON COLUMN BOOK.AUTHOR_ID IS 'Идентификатор автора';
COMMENT ON COLUMN BOOK.GENRE_ID IS 'Идентификатор жанра';


DROP TABLE IF EXISTS COMMENT;
CREATE TABLE COMMENT(
                      ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                      BOOK_ID BIGINT NOT NULL,
                      CONTENT VARCHAR(255) NOT NULL,
                      FOREIGN KEY (BOOK_ID) REFERENCES BOOK (ID) ON DELETE CASCADE ON UPDATE CASCADE
);
COMMENT ON TABLE COMMENT IS 'Комментарии';
COMMENT ON COLUMN COMMENT.ID IS 'Идентификатор';
COMMENT ON COLUMN COMMENT.BOOK_ID IS 'Идентификатор книги';
COMMENT ON COLUMN COMMENT.CONTENT IS 'Содержание';


CREATE TABLE USER
(
    ID BIGINT NOT NULL AUTO_INCREMENT  PRIMARY KEY,
    USERNAME VARCHAR(255) NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL
);


CREATE TABLE ROLE
(
    ID BIGINT NOT NULL AUTO_INCREMENT  PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL
);


CREATE TABLE USER_ROLE
(
    USER_ID BIGINT,
    ROLE_ID BIGINT,
    FOREIGN KEY (USER_ID) REFERENCES USER (ID) ON DELETE CASCADE,
    FOREIGN KEY (ROLE_ID) REFERENCES ROLE (ID) ON DELETE CASCADE
);