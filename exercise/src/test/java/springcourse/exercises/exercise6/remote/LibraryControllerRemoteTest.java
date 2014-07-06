package springcourse.exercises.exercise6.remote;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import springcourse.exercises.exercise6.model.Book;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Amit Tal
 * @since 4/13/2014
 */
public class LibraryControllerRemoteTest {

    private Logger logger = LoggerFactory.getLogger(LibraryControllerRemoteTest.class);

    private final String baseUrl = "http://localhost:9090/";

    private RestTemplate restTemplate;


    @Before
    public void setup() {
        this.restTemplate = new RestTemplate();
    }

    @Test
    @Ignore
    public void testHappyFlow() {

        // Read initial library books
        logger.info("Read all books");
        Collection<Book> initialLibraryBooks = readAllLibraryBooks();
        Assert.assertNotNull("No books in library", initialLibraryBooks);
        int numOfInitialBooks = initialLibraryBooks.size();

        // Create book
        logger.info("Create book");
        Book newBook = createBook();
        Assert.assertNotNull("Book is null", newBook);
        Assert.assertNotNull("Book catalogId is null", newBook.getCatalogId());

        // Read (again) all books and verify new book exists
        logger.info("Read all books 2");
        Collection<Book> allLibraryBooks2 = readAllLibraryBooks();
        Assert.assertNotNull("No books in library", allLibraryBooks2);
        Assert.assertEquals("Expected number of books to grow by one", numOfInitialBooks + 1, allLibraryBooks2.size());
        assertBookExists(allLibraryBooks2, newBook);

        // Read books by author of new book
        logger.info("Read books by author: {}", newBook.getAuthor());
        Collection<Book> booksByAuthor = readBooksByAuthor(newBook.getAuthor());
        Assert.assertNotNull("No books found by author", booksByAuthor);
        Assert.assertTrue("Expected at least one book by author", booksByAuthor.size() >= 1);
        assertBookExists(booksByAuthor, newBook);

        // Delete book
        logger.info("Delete book: {}", newBook.getCatalogId());
        deleteBook(newBook.getCatalogId());

        // Read (again) all books and verify new book does not exists
        logger.info("Read all books 3");
        Collection<Book> allLibraryBooks3 = readAllLibraryBooks();
        Assert.assertNotNull(allLibraryBooks3);
        Assert.assertEquals("Expected number of books to grow by one", numOfInitialBooks, allLibraryBooks3.size());
        assertBookNotExists(allLibraryBooks3, newBook);
    }

    private Collection<Book> readAllLibraryBooks() {
        Book[] response = restTemplate.getForObject(baseUrl + "/books", Book[].class);
        return Arrays.asList(response);
    }

    private Book createBook() {
        // TODO Implement method, including sending the request to the server
        // TODO Implement method, including sending the request to the server
        // TODO Implement method, including sending the request to the server
        return null;
    }

    private Collection<Book> readBooksByAuthor(String author) {
        // TODO Implement method, including sending the request to the server
        // TODO Implement method, including sending the request to the server
        // TODO Implement method, including sending the request to the server
        return null;
    }

    private void deleteBook(String catalogId) {
        restTemplate.delete(baseUrl + "/books/{catalogId}", catalogId);
    }


    private void assertBookExists(Collection<Book> books, Book book) {
        assertBookExistence(books, book, true);
    }

    private void assertBookNotExists(Collection<Book> books, Book book) {
        assertBookExistence(books, book, false);
    }

    private void assertBookExistence(Collection<Book> books, Book book, boolean assertBookExists) {
        Assert.assertNotNull("No books exist", books);
        Assert.assertFalse("No books exist", books.isEmpty());
        Assert.assertNotNull("Book is null",  book);
        Assert.assertNotNull("Book catalog is missing",  book.getCatalogId());
        boolean bookFound = false;
        for (Book b : books) {
            if (b.equals(book)) {
                bookFound = true;
                break;
            }
        }
        if (assertBookExists) {
            Assert.assertTrue(bookFound);
        } else {
            Assert.assertFalse(bookFound);
        }
    }
}

