package repository.book;

import model.Book;
import model.Sale;

import java.util.*;

public class BookRepositoryMock implements BookRepository {
    private final List<Book> books;
    private final List<Sale> sales;

    public BookRepositoryMock() {
        books = new ArrayList<>();
        sales = new ArrayList<>();
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public List<Sale> findAllSale() {
        return sales;
    }

    @Override
    public boolean ifBookIsPresent(String title) {
        Optional<Book> book = books.stream().filter(b -> b.getTitle().equals(title)).findFirst();

        if (book.isPresent()) {
            return true;
        }

        return false;
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return books.parallelStream()
                .filter(it -> it.getTitle().equals(title))
                .findFirst();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean updateAmount(String title, int newAmount, int quantity, double price) {
        Optional<Book> bookFind = books.parallelStream()
                .filter(It -> It.getTitle().equals(title))
                .findFirst();

        if (bookFind.isPresent()) {
            Book book = bookFind.get();
            book.setAmount(newAmount);
            return true;
        }

        return false;
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }

    @Override
    public boolean delete(Book book) {
        return books.remove(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }
}
