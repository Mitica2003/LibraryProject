package controller;

import mapper.BookMapper;
import mapper.UserMapper;
import model.Book;
import model.validator.Notification;
import service.book.BookService;
import service.pdf.PDFService;
import service.user.AuthenticationService;
import view.AdminView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;
import view.model.builder.SaleDTOBuilder;
import view.model.builder.UserDTOBuilder;
import view.model.SaleDTO;
import view.model.UserDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminController {
    private final AdminView adminView;
    private final BookService bookService;
    private final AuthenticationService authenticationService;
    private final PDFService pdfService;
    private static final Long adminID = 1L;

    public AdminController(AdminView adminView, BookService bookService, AuthenticationService authenticationService) {
        this.adminView = adminView;
        this.bookService = bookService;
        this.authenticationService = authenticationService;

        this.pdfService = new PDFService();

        this.adminView.addSaveButtonListener(new AdminController.SaveButtonListener());
        this.adminView.addSelectionTableListener(new AdminController.SelectionTableListener());
        this.adminView.addDeleteButtonListener(new AdminController.DeleteButtonListener());
        this.adminView.addSaleButtonListener(new AdminController.SaleButtonListener());
        this.adminView.addRegisterUserListener(new AdminController.RegisterButtonListener());
        this.adminView.addDeleteUserListener(new AdminController.FireButtonListener());

        this.adminView.addGenerateReportButtonListener(new AdminController.GenerateReportButtonListener());
    }


    private class SaveButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String title = adminView.getTitle();
            String author = adminView.getAuthor();

            Integer amount = adminView.getAmount();
            Double price = adminView.getPrice();

            if (title.isEmpty() || author.isEmpty() || amount <= 0 || price <= 0.0){
                adminView.displayAlertMessage("Save Error", "Problem at Title, Author, Amount or Price fields", "Can not have empty/negative Author, Title, Amount or Price fields. Please fill in the fields correctly before submitting Save!");
                adminView.getBooksObservableList().get(0).setTitle("No Name");
            } else {
                BookDTO bookDTO = new BookDTOBuilder().setAuthor(author).setTitle(title).setAmount(amount).setPrice(price).build();
                boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));

                if (savedBook) {
                    adminView.displayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                    adminView.addBookToObservableList(bookDTO);
                } else {
                    adminView.displayAlertMessage("Save Not Successful", "Book was not added", "There was a problem at adding the book into the database.");
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
            BookDTO bookDTO = (BookDTO) adminView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null){
                boolean deletionSuccessfull = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if (deletionSuccessfull){
                    adminView.displayAlertMessage("Delete Successful", "Book Deleted", "Book was successfully deleted from database.");
                    adminView.removeBookFromObservableList(bookDTO);
                } else {
                    adminView.displayAlertMessage("Deletion not successful", "Deletion Process", "There was a problem in the deletion process. Please restart the application and try again!");
                }
            } else {
                adminView.displayAlertMessage("Deletion not successful", "Deletion Process", "You need to select a row from table before pressing the delete button!");
            }
        }
    }

    private class SaleButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String bookTitle = adminView.getSaleTitleBook();
            Integer quantity = adminView.getSaleQuantity();

            if (bookTitle.isEmpty() || quantity <= 0) {
                adminView.displayAlertMessage("Error", "Problem at fields title, quantity", "Can not have empty/negative Title or quantity.");
                adminView.getSaleObservableList().get(0).setBookTitle("No Name");
            } else {
                boolean findSuccessfull = bookService.ifBookIsPresent(bookTitle);

                if (findSuccessfull) {
                    Book book = bookService.findByTitle(bookTitle);
                    BookDTO bookDTO = BookMapper.convertBookToBookDTO(book);

                    int newAmount = bookDTO.getAmount() - quantity;
                    if (newAmount < 0) {
                        adminView.displayAlertMessage("Sale Error", "Sale was not procesed", "You sold more books than the quantity");
                    }
                    else {
                        boolean actionModifiy = bookService.updateAmount(bookDTO.getTitle(), newAmount, quantity, bookDTO.getPrice() * quantity);

                        SaleDTO saleDTO = new SaleDTOBuilder()
                                .setBookTitle(bookTitle)
                                .setQuantity(quantity)
                                .setTotalPrice(quantity * bookDTO.getPrice())
                                .setID(adminID)
                                .setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .build();

                        adminView.addSaleToObservableList(saleDTO);

                        if (actionModifiy) {
                            adminView.displayAlertMessage("Order Finish", "Order was procesed", "Order was procesesd from database.");
                            bookDTO.setAmount(newAmount);
                            adminView.updateBookToObservabileList(bookDTO);

                            if (bookDTO.getAmount() == 0) {
                                boolean succ = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                                if (succ) {
                                    adminView.displayAlertMessage("Book eliminated", "Book Deleted", "Book was deleted from database because of amount value (amount = 0)!");
                                    adminView.removeBookFromObservableList(bookDTO);
                                }
                            }
                        } else {
                            adminView.displayAlertMessage("Order Error", "Order was not procesed", "There was a problem in the book process. Please restart the application and try again!");
                        }
                    }
                } else {
                    adminView.displayAlertMessage("Order Error", "Book not found", "Book was not fount in database.");
                }
            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.registerEmployee(username, password);

            if (registerNotification.hasErrors()) {
                adminView.displayAlertMessage("Register problem", "Employee not registered!", "Employee was not registered.");
            } else {
                adminView.displayAlertMessage("Register successful!", "Employee registered", "Employee was successfully registered.");

                UserDTO newUserDTO = new UserDTOBuilder()
                        .setUsername(username)
                        .build();

                adminView.getUsersObservabileList().add(newUserDTO);
                adminView.getUserTableView().refresh();
            }
        }
    }

    private class FireButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            UserDTO selectedUser = (UserDTO) adminView.getUserTableView().getSelectionModel().getSelectedItem();

            if (selectedUser == null) {
                adminView.displayAlertMessage("Deletion Error", "No Selection",
                        "Please select a user to delete.");
                return;
            }

            boolean deletionSuccessful = authenticationService.deleteUser(UserMapper.convertUserDTOToUser(selectedUser));

            if (deletionSuccessful) {
                adminView.removeUserFromObservableList(selectedUser);

                adminView.getUserTableView().refresh();

                adminView.displayAlertMessage("Deletion Successful", "User Deleted",
                        "User was successfully deleted.");
            } else {
                adminView.displayAlertMessage("Deletion Error", "Database Error",
                        "There was an error deleting the user.");
            }
        }
    }

    private class GenerateReportButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                List<SaleDTO> sales = adminView.getSaleObservableList();

                String filePath = "ReportOfSales.pdf";
                pdfService.generateSalesReport(filePath, sales);

                adminView.displayAlertMessage("PDF Generated", "The PDF was generated", "PDF was generated with the name " + filePath);

            } catch (Exception e) {
                e.printStackTrace();
                adminView.displayAlertMessage("PDF Not Generated", "The PDF was not generated", "PDF was not generated.");
            }
        }
    }
}
