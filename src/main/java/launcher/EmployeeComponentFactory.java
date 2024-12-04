package launcher;

import controller.BookController;
import database.DatabaseConnectionFactory;
import mapper.BookMapper;
import mapper.SaleMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import view.BookView;
import view.model.BookDTO;
import view.model.SaleDTO;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.*;

public class EmployeeComponentFactory {
    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private static EmployeeComponentFactory instance;

    public static EmployeeComponentFactory getInstance(Boolean componentsForTest, Stage stage) {
        if (instance == null) {
            synchronized (EmployeeComponentFactory.class) {
                if (instance == null) {
                    instance = new EmployeeComponentFactory(componentsForTest, stage);
                }
            }
        }

        return instance;
    }

    private EmployeeComponentFactory(Boolean componentsForTest, Stage stage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryMySQL(connection);

        this.bookService = new BookServiceImpl(bookRepository);

        List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(this.bookService.findAll());
        List<SaleDTO> saleDTOs = SaleMapper.convertSaleListToSaleDTOList(this.bookService.findAllSale());

        this.bookView = new BookView(stage, bookDTOs, saleDTOs);
        this.bookController = new BookController(bookView, bookService);
    }

    // Getter pentru componentele interne
    public BookView getBookView() {
        return bookView;
    }

    public BookController getBookController() {
        return bookController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }
}
