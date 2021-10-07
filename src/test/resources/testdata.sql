INSERT INTO AUTHOR (id, name) values (1, 'Author-1');
INSERT INTO AUTHOR (id, name) values (2, 'Author-2');
INSERT INTO AUTHOR (id, name) values (3,'Author-3');

INSERT INTO GENRE (id, name) values (1, 'Genre-1');
INSERT INTO GENRE (id, name) values (2, 'Genre-2');
INSERT INTO GENRE (id, name) values (3, 'Genre-3');

INSERT INTO BOOK (title, author_id, genre_id) values ('Book-1', 1, 1);
INSERT INTO BOOK (title, author_id, genre_id) values ('Book-2', 1, 2);
INSERT INTO BOOK (title, author_id, genre_id) values ('Book-3', 1, 3);

INSERT INTO COMMENT (book_id, content) values (1, 'Good');
INSERT INTO COMMENT (book_id, content) values (1, 'Bad');


INSERT INTO ROLE(ID, NAME) VALUES (1, 'USER');
INSERT INTO ROLE(ID, NAME) VALUES (2, 'ADMIN');

INSERT INTO USER(ID, USERNAME, PASSWORD) VALUES (1, 'user', 'password');
INSERT INTO USER(ID, USERNAME, PASSWORD) VALUES (2, 'admin', 'password');

INSERT INTO USER_ROLE(USER_ID, ROLE_ID) VALUES (1, 1);
INSERT INTO USER_ROLE(USER_ID, ROLE_ID) VALUES (2, 2);