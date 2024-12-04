package controller;

import mapper.BookMapper;
import model.Book;
import service.book.BookService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;
import view.model.builder.SaleDTOBuilder;
import view.model.SaleDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookController {
    private final BookView bookView;
    private final BookService bookService;
    private static final Long employeeID = 2L;
    public BookController(BookView bookView, BookService bookService) {
        this.bookView = bookView;
        this.bookService = bookService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addSelectionTableListener(new SelectionTableListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addSaleButtonListener(new SaleButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();

            Integer amount = bookView.getAmount();
            Double price = bookView.getPrice();

            if (title.isEmpty() || author.isEmpty() || amount <= 0 || price <= 0.0){
                bookView.displayAlertMessage("Save Error", "Problem at Title, Author, Amount or Price fields", "Can not have empty/negative Author, Title, Amount or Price fields. Please fill in the fields correctly before submitting Save!");
                bookView.getBooksObservableList().get(0).setTitle("No Name");
            } else {
                BookDTO bookDTO = new BookDTOBuilder().setAuthor(author).setTitle(title).setAmount(amount).setPrice(price).build();
                boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));

                if (savedBook) {
                    bookView.displayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                    bookView.addBookToObservableList(bookDTO);
                } else {
                    bookView.displayAlertMessage("Save Not Successful", "Book was not added", "There was a problem at adding the book into the database.");
                }
            }
        }
    }

    private class SelectionTableListener implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            BookDTO selectedBookDTO = (BookDTO) newValue;

            if (selectedBookDTO != null) {
                System.out.println("Book Author: " + selectedBookDTO.getAuthor() + " Title: " + selectedBookDTO.getTitle());
            }
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null){
                boolean deletionSuccessfull = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if (deletionSuccessfull){
                    bookView.displayAlertMessage("Delete Successful", "Book Deleted", "Book was successfully deleted from database.");
                    bookView.removeBookFromObservableList(bookDTO);
                } else {
                    bookView.displayAlertMessage("Deletion not successful", "Deletion Process", "There was a problem in the deletion process. Please restart the application and try again!");
                }
            } else {
                bookView.displayAlertMessage("Deletion not successful", "Deletion Process", "You need to select a row from table before pressing the delete button!");
            }
        }
    }

    private class SaleButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String bookTitle = bookView.getSaleTitleBook();
            Integer quantity = bookView.getSaleQuantity();

            if (bookTitle.isEmpty() || quantity <= 0) {
                bookView.displayAlertMessage("Error", "Problem at fields title, quantity", "Can not have empty/negative Title or quantity.");
                bookView.getSaleObservableList().get(0).setBookTitle("No Name");
            } else {
                boolean findSuccessfull = bookService.ifBookIsPresent(bookTitle);

                if (findSuccessfull) {
                    Book book = bookService.findByTitle(bookTitle);
                    BookDTO bookDTO = BookMapper.convertBookToBookDTO(book);

                    int newAmount = bookDTO.getAmount() - quantity;
                    if (newAmount < 0) {
                        bookView.displayAlertMessage("Sale Error", "Sale was not procesed", "You sold more books than the quantity");
                    }
                    else {
                        boolean actionModifiy = bookService.updateAmount(bookDTO.getTitle(), newAmount, quantity, bookDTO.getPrice() * quantity);

                        SaleDTO saleDTO = new SaleDTOBuilder()
                                .setBookTitle(bookTitle)
                                .setQuantity(quantity)
                                .setTotalPrice(quantity * bookDTO.getPrice())
                                .setID(employeeID)
                                .setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .build();

                        bookView.addSaleToObservableList(saleDTO);

                        if (actionModifiy) {
                            bookView.displayAlertMessage("Order Finish", "Order was procesed", "Order was procesesd from database.");
                            bookDTO.setAmount(newAmount);
                            bookView.updateBookToObservabileList(bookDTO);

                            if (bookDTO.getAmount() == 0) {
                                boolean succ = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                                if (succ) {
                                    bookView.displayAlertMessage("Book eliminated", "Book Deleted", "Book was deleted from database because of amount value (amount = 0)!");
                                    bookView.removeBookFromObservableList(bookDTO);
                                }
                            }
                        } else {
                            bookView.displayAlertMessage("Order Error", "Order was not procesed", "There was a problem in the book process. Please restart the application and try again!");
                        }
                    }
                } else {
                    bookView.displayAlertMessage("Order Error", "Book not found", "Book was not fount in database.");
                }
            }
        }
    }
}

