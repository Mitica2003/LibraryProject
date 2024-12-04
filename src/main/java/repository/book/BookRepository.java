package repository.book;

import model.Book;
import model.Sale;

import java.util.*;

public interface BookRepository {
    List<Book> findAll();

    List<Sale> findAllSale();

    boolean ifBookIsPresent(String title);

    Optional<Book> findByTitle(String title);

    Optional<Book> findById(Long id);

    boolean updateAmount(String title, int amount, int quantity, double price);

    boolean save(Book book);

    boolean delete(Book book);

    void removeAll();
}
