package ru.cherry.springhomework.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.cherry.springhomework.domain.Author;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Comment;
import ru.cherry.springhomework.domain.Genre;
import ru.cherry.springhomework.security.UserService;
import ru.cherry.springhomework.service.AuthorService;
import ru.cherry.springhomework.service.BookService;
import ru.cherry.springhomework.service.CommentService;
import ru.cherry.springhomework.service.GenreService;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    CommentService commentService;
    @MockBean
    private UserService userService;


    @ParameterizedTest
    @ValueSource(strings = {"/", "/viewbook?id=1", "/deletebook?id=1"})
    void notAuthenticatedGetTest(String value) throws Exception {
        mockMvc.perform(get(value)).andExpect(unauthenticated());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/addbook", "/savebook"})
    void notAuthenticatedPostTest(String value) throws Exception {
        mockMvc.perform(post(value)).andExpect(unauthenticated());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    void getAllBooksPositiveTest() throws Exception {
        Author author = new Author(1L, "Author-1");
        Genre genre = new Genre(1L, "Genre-1");
        Book book1 = new Book(1L, "Book-1", author, genre);
        Book book2 = new Book(1L, "Book-2", author, genre);
        List<Book> books = List.of(book1, book2);
        given(bookService.getAllBooks()).willReturn(books);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", equalTo(books)));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void editBookPositiveTest() throws Exception {
        Author author = new Author(1L, "Author-1");
        Genre genre = new Genre(1L, "Genre-1");
        Book book = new Book(1L, "Book-1", author, genre);
        given(bookService.getById(1L)).willReturn(book);

        mockMvc.perform(get("/editbook?id=1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", equalTo(book)));
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    void editBookNegativeTest() throws Exception {
        Author author = new Author(1L, "Author-1");
        Genre genre = new Genre(1L, "Genre-1");
        Book book = new Book(1L, "Book-1", author, genre);
        given(bookService.getById(1L)).willReturn(book);

        mockMvc.perform(get("/editbook?id=1"))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void addNewBookPositiveTest() throws Exception {
        Author author = new Author(1L, "Author-1");
        Genre genre = new Genre(1L, "Genre-1");
        Book book = new Book(1L, "Book-1", author, genre);
        List<Author> authors = List.of(author);
        List<Genre> genres = List.of(genre);
        given(authorService.getAllAuthors()).willReturn(authors);
        given(genreService.getAllGenres()).willReturn(genres);

        mockMvc.perform(get("/addbook"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("authors", equalTo(authors)))
                .andExpect(model().attribute("genres", equalTo(genres)));
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    void addNewBookNegativeTest() throws Exception {
        Author author = new Author(1L, "Author-1");
        Genre genre = new Genre(1L, "Genre-1");
        Book book = new Book(1L, "Book-1", author, genre);
        List<Author> authors = List.of(author);
        List<Genre> genres = List.of(genre);
        given(authorService.getAllAuthors()).willReturn(authors);
        given(genreService.getAllGenres()).willReturn(genres);

        mockMvc.perform(get("/addbook"))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    void viewBookPositiveTest() throws Exception {
        Author author = new Author(1L, "Author-1");
        Genre genre = new Genre(1L, "Genre-1");
        Book book = new Book(1L, "Book-1", author, genre);
        Comment comment = new Comment(book, "Good");
        book.setComments(List.of(comment));
        given(bookService.getById(1L)).willReturn(book);

        mockMvc.perform(get("/viewbook?id=1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", equalTo(book)));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void deleteBookPositiveTest() throws Exception {
        Author author = new Author(1L, "Author-1");
        Genre genre = new Genre(1L, "Genre-1");
        Book book = new Book(1L, "Book-1", author, genre);
        given(bookService.getById(1L)).willReturn(book);
        given(bookService.deleteBook(1L)).willReturn(true);

        mockMvc.perform(get("/deletebook?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    void deleteBookNegativeTest() throws Exception {
        Author author = new Author(1L, "Author-1");
        Genre genre = new Genre(1L, "Genre-1");
        Book book = new Book(1L, "Book-1", author, genre);
        given(bookService.getById(1L)).willReturn(book);
        given(bookService.deleteBook(1L)).willReturn(true);

        mockMvc.perform(get("/deletebook?id=1"))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void savebookPositiveTest() throws Exception {
        Author author = new Author(1L, "Author-1");
        Genre genre = new Genre(1L, "Genre-1");
        Book book = new Book(1L, "Book-1", author, genre);

        mockMvc.perform(post("/savebook")
                .contentType(MediaType.APPLICATION_JSON)
                .flashAttr("book", book))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
        verify(bookService, times(1)).save(book);
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    void saveBookNegativeTest() throws Exception {
        Author author = new Author(1L, "Author-1");
        Genre genre = new Genre(1L, "Genre-1");
        Book book = new Book(1L, "Book-1", author, genre);

        mockMvc.perform(post("/savebook")
                .contentType(MediaType.APPLICATION_JSON)
                .flashAttr("book", book))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void saveBookChangesPositiveTest() throws Exception {
        Author author = new Author(1L, "Author-1");
        Genre genre = new Genre(1L, "Genre-1");
        Book book = new Book(1L, "Book-1", author, genre);
        given(bookService.getById(1L)).willReturn(book);

        mockMvc.perform(post("/savebookchanges")
                .contentType(MediaType.APPLICATION_JSON)
                .flashAttr("book", book))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
        verify(bookService, times(1)).save(book);
    }

    @WithMockUser(username = "user", authorities = {"USER"})
    @Test
    void saveBookChangesNegativeTest() throws Exception {
        Author author = new Author(1L, "Author-1");
        Genre genre = new Genre(1L, "Genre-1");
        Book book = new Book(1L, "Book-1", author, genre);
        given(bookService.getById(1L)).willReturn(book);

        mockMvc.perform(post("/savebookchanges")
                .contentType(MediaType.APPLICATION_JSON)
                .flashAttr("book", book))
                .andExpect(status().is4xxClientError());
    }

}