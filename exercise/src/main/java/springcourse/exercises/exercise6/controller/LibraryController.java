package springcourse.exercises.exercise6.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springcourse.exercises.exercise6.model.Book;
import springcourse.exercises.exercise6.service.api.ILibrary;

import java.util.Collection;

/**
 * @author Amit Tal
 * @since 4/6/14
 */
// TODO Add proper spring bean stereotype
// TODO Add url mapping so all the methods in this controller urls will start with "/books"
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

    // TODO Restify method (add appropriate annotations)
    public void deleteBook(String catalogId) {
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

    // TODO Restify method (add appropriate annotations)
    // TODO NOTICE that the author is passed via url param
    // TODO        for example the url to this method might be: http://localhost:8080/books?author=Lewis Carroll
    public Collection<Book> readBooksByAuthor(String author) {
        logger.info("Request to read all books by author {}", author);
        Collection<Book> books = library.searchBooksByAuthor(author);
        logger.info("read {} books by author {}", (books == null ? 0 : books.size()), author);
        return books;
    }
}
