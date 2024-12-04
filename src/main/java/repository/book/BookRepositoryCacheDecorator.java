package repository.book;

import model.Book;
import model.Sale;

import java.util.List;
import java.util.Optional;

public class BookRepositoryCacheDecorator extends BookRepositoryDecorator {
    private Cache<Book> cache;
    private Cache<Sale> saleCache;

    public BookRepositoryCacheDecorator(BookRepository bookRepository, Cache<Book> cache, Cache<Sale> saleCache) {
        super(bookRepository);
        this.cache = cache;
        this.saleCache = saleCache;
    }

    @Override
    public List<Book> findAll() {
        if (cache.hasResult()) {
            return cache.load();
        }

        List<Book> books = decoratedBookRepository.findAll();
        cache.save(books);

        return books;
    }

    @Override
    public List<Sale> findAllSale() {
        if (saleCache.hasResult()) {
            return saleCache.load();
        }

        List<Sale> sales = decoratedBookRepository.findAllSale();
        saleCache.save(sales);

        return sales;
    }

    @Override
    public boolean ifBookIsPresent(String title) {
        cache.invalidateChache();
        saleCache.invalidateChache();

        return decoratedBookRepository.ifBookIsPresent(title);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        if (cache.hasResult()) {
            return cache.load().stream()
                    .filter(it -> it.getTitle().equals(title))
                    .findFirst();
        }

        return decoratedBookRepository.findByTitle(title);
    }

    @Override
    public Optional<Book> findById(Long id) {
        if (cache.hasResult()) {
            return cache.load().stream()
                    .filter(it -> it.getId().equals(id))
                    .findFirst();
        }

        return decoratedBookRepository.findById(id);
    }

    @Override
    public boolean updateAmount(String title, int amount, int quantity, double price) {
        cache.invalidateChache();
        saleCache.invalidateChache();

        return decoratedBookRepository.updateAmount(title, amount, quantity, price);
    }

    @Override
    public boolean save(Book book) {
        cache.invalidateChache();

        return decoratedBookRepository.save(book);
    }

    @Override
    public boolean delete(Book book) {
        cache.invalidateChache();

        return decoratedBookRepository.delete(book);
    }

    @Override
    public void removeAll() {
        cache.invalidateChache();

        decoratedBookRepository.removeAll();
    }
}
