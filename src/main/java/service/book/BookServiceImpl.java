package service.book;

import model.Book;
import model.Sale;
import repository.book.BookRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final List<Sale> sales;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.sales = new ArrayList<>();
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Sale> findAllSale() {return bookRepository.findAllSale();}

    @Override
    public boolean ifBookIsPresent(String title) {
        return bookRepository.ifBookIsPresent(title);
    }

    @Override
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("Book with title: %s was not found".formatted(title)));
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with id: %d was not found".formatted(id)));
    }

    @Override
    public boolean updateAmount(String title, int newAmount, int quantity, double price){
        return bookRepository.updateAmount(title, newAmount, quantity, price);
    }

    @Override
    public boolean save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public boolean delete(Book book) {
        return bookRepository.delete(book);
    }

    @Override
    public int getAgeOfBook(Long id) {
        Book book = this.findById(id);
        LocalDate now = LocalDate.now();

        return (int) ChronoUnit.YEARS.between(book.getPublishedDate(), now);
    }
}
