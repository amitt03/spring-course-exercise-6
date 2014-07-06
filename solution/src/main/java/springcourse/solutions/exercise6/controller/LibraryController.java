package springcourse.solutions.exercise6.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springcourse.solutions.exercise6.model.Book;
import springcourse.solutions.exercise6.service.api.ILibrary;

import java.util.Collection;

/**
 * @author Amit Tal
 * @since 4/6/14
 */
@RestController
@RequestMapping(value = "/books", produces = "application/json")
public class LibraryController {

    private Logger logger = LoggerFactory.getLogger(LibraryController.class);

    private ILibrary library;

    @Autowired
    public void setLibrary(ILibrary library) {
        this.library = library;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        logger.info("Request to create book {}", book);
        Book result = library.addNewBook(book);
        logger.info("Created book {}", result);
        return result;
    }

    @RequestMapping(value = "/{catalogId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable String catalogId) {
        logger.info("Request to delete book {}", catalogId);
        library.removeBook(catalogId);
        logger.info("Book {} deleted", catalogId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Book> readAllBooks() {
        logger.info("Request to read all books");
        Collection<Book> allBooks = library.getAllBooks();
        logger.info("read {} books", (allBooks == null ? 0 : allBooks.size()));
        return allBooks;
    }

    @RequestMapping(method = RequestMethod.GET, params = "author")
    public Collection<Book> readBooksByAuthor(@RequestParam String author) {
        logger.info("Request to read all books by author {}", author);
        Collection<Book> books = library.searchBooksByAuthor(author);
        logger.info("read {} books by author {}", (books == null ? 0 : books.size()), author);
        return books;
    }
}
