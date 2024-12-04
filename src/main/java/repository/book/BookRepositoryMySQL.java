package repository.book;

import model.Book;
import model.Sale;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.Optional;

public class BookRepositoryMySQL implements BookRepository {
    private final Connection connection;

    public BookRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book;";

        List<Book> books = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Long id = resultSet.getLong("id");
                String author = resultSet.getString("author");
                String title = resultSet.getString("title");
                Date publishedDate = resultSet.getDate("publishedDate");

                int amount = resultSet.getInt("amount");
                double price = resultSet.getDouble("price");

                Book book = new Book();

                book.setId(id);
                book.setAuthor(author);
                book.setTitle(title);
                book.setPublishedDate(publishedDate.toLocalDate());

                book.setAmount(amount);
                book.setPrice(price);

                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public boolean ifBookIsPresent(String title) {
        String sql = "SELECT * FROM book WHERE title=?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Optional<Book> findByTitle(String title1) {
        String sql = "SELECT * FROM book WHERE title = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title1);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String author = resultSet.getString("author");
                String title = resultSet.getString("title");
                Date publishedDate = resultSet.getDate("publishedDate");
                int amount = resultSet.getInt("amount");
                double price = resultSet.getDouble("price");

                Book book = new Book();

                book.setId(id);
                book.setTitle(title);
                book.setAuthor(author);
                book.setPublishedDate(publishedDate.toLocalDate());
                book.setAmount(amount);
                book.setPrice(price);

                return Optional.of(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = " + id;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String author = resultSet.getString("author");
                String title = resultSet.getString("title");
                Date publishedDate = resultSet.getDate("publishedDate");

                int amount = resultSet.getInt("amount");
                double price = resultSet.getDouble("price");

                Book book = new Book();

                book.setId(id);
                book.setAuthor(author);
                book.setTitle(title);
                book.setPublishedDate(publishedDate.toLocalDate());

                book.setAmount(amount);
                book.setPrice(price);

                return Optional.of(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //return book;
        return Optional.empty();
    }

    @Override
    public boolean updateAmount(String title, int newAmount, int quantity, double price) {
        String sql = "UPDATE book SET amount = ? WHERE title = ?";
        String newsql = "INSERT INTO sale VALUES(null,?,?,?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newAmount);
            preparedStatement.setString(2, title);

            int rowsUpdated = preparedStatement.executeUpdate();

            PreparedStatement preparedStatement1 = connection.prepareStatement(newsql);
            preparedStatement1.setString(1, title);
            preparedStatement1.setInt(2, quantity);
            preparedStatement1.setDouble(3, price);

            int rowsUpdated1 = preparedStatement1.executeUpdate();

            return rowsUpdated == 1 && rowsUpdated1 == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Sale> findAllSale() {
        String sql = "SELECT * FROM sale;";

        List<Sale> sales = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Long id = resultSet.getLong("id");
                String booktitle = resultSet.getString("title");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");

                Sale sale = new Sale();

                sale.setId(id);
                sale.setBookTitle(booktitle);
                sale.setQuantity(quantity);
                sale.setTotalPrice(price);

                sales.add(sale);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }

    @Override
    public boolean save(Book book) {
        String newSql = "INSERT INTO book VALUES(null,?,?,?,?,?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));

            preparedStatement.setInt(4, book.getAmount());
            preparedStatement.setDouble(5, book.getPrice());

            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted == 1;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Book book) {
        String newSql = "DELETE FROM book WHERE author = ? AND title = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());

            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted == 1;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        String sql = "DELETE FROM book WHERE id >= 0;";
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
