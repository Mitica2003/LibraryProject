import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.book.BookRepository;
import repository.book.BookRepositoryMock;
import repository.book.BookRepositoryMySQL;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryMySQLTest {
    private static Connection connection = null;
    private static BookRepository bookRepository;
    @BeforeAll
    public static void setup(){
        connection = DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();
        bookRepository = new BookRepositoryMySQL(connection);
    }

    @Test
    public void findAll(){
        List<Book> books = bookRepository.findAll();
        assertEquals(1, books.size());
    }

    @Test
    public void findById(){
        final Optional<Book> book = bookRepository.findById(1L);
        assertTrue(!book.isEmpty());
    }

    @Test
    public void save(){
        assertTrue(bookRepository.save(new BookBuilder().setTitle("Ion").setAuthor("Liviu Rebreanu").setPublishedDate(LocalDate.of(1900, 10, 2)).build()));
    }
}
