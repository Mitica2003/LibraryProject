package view;

import view.model.BookDTO;
import view.model.SaleDTO;
import view.model.UserDTO;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class AdminView {
    private TableView bookTableView;
    private TableView saleTableView;
    private TableView userTableView;
    private ObservableList<BookDTO> booksObservableList;
    private ObservableList<SaleDTO> saleObservableList;
    private ObservableList<UserDTO> usersObservabileList;
    private TextField authorTextField;
    private TextField titleTextField;
    private TextField amounTextField;
    private TextField priceTextField;
    private TextField saleTitleBookTextField;
    private TextField saleQuantityTextField;
    private TextField usernameTextField;
    private TextField passwordTextField;
    private Label authorLabel;
    private Label titleLabel;
    private Label amountLabel;
    private Label priceLabel;
    private Label saleTitleBookLabel;
    private Label saleQuantityLabel;
    private Label usernameLabel;
    private Label passwordLabel;
    private Button saveButton;
    private Button deleteButton;
    private Button saleButton;
    private Button registerUserButton;
    private Button fireUserButton;
    private Button pdfButton;

    public AdminView(Stage primaryStage, List<BookDTO> bookDTOS, List<SaleDTO> saleDTOS, List<UserDTO> userDTOs) {
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 1500, 600);
        primaryStage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(bookDTOS);
        saleObservableList = FXCollections.observableArrayList(saleDTOS);
        usersObservabileList = FXCollections.observableArrayList(userDTOs);

        initTableViewBook(gridPane);
        initTableSale(gridPane);
        initTableViewUsers(gridPane);

        initSaveOptions(gridPane);
        initAdminOptions(gridPane);

        primaryStage.show();
    }

    private void initTableViewBook(GridPane gridPane) {
        bookTableView = new TableView<BookDTO>();
        bookTableView.setPlaceholder(new Label("No rows to display"));

        TableColumn<BookDTO, String> titleColumn = new TableColumn<BookDTO, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BookDTO, String> authorColumn = new TableColumn<BookDTO, String>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<BookDTO, Number> amountColumn = new TableColumn<BookDTO, Number>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<BookDTO, Number> priceColumn = new TableColumn<BookDTO, Number>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        bookTableView.getColumns().addAll(titleColumn, authorColumn, amountColumn, priceColumn);

        bookTableView.setItems(booksObservableList);

        gridPane.add(bookTableView,0,0, 5,1);
    }

    private void initTableSale(GridPane gridPane) {
        saleTableView = new TableView<SaleDTO>();
        saleTableView.setPlaceholder(new Label("No rows to display"));

        TableColumn<SaleDTO, String> bookTitleColumn = new TableColumn<SaleDTO, String>("Title Book");
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));

        TableColumn<SaleDTO, Number> quantityColumn = new TableColumn<SaleDTO, Number>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<SaleDTO, Number> totalPriceColumn = new TableColumn<SaleDTO, Number>("Total price");
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        saleTableView.getColumns().addAll(bookTitleColumn, quantityColumn, totalPriceColumn);

        saleTableView.setItems(saleObservableList);

        gridPane.add(saleTableView,5,0, 5,1);
    }

    private void initTableViewUsers(GridPane gridPane) {
        userTableView = new TableView<UserDTO>();
        userTableView.setPlaceholder(new Label("No rows to display"));

        TableColumn<UserDTO, String> usernameColumn = new TableColumn<UserDTO, String>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        userTableView.getColumns().addAll(usernameColumn);

        userTableView.setItems(usersObservabileList);

        gridPane.add(userTableView,10, 0, 5, 1);
    }

    private void initSaveOptions(GridPane gridPane){
        titleLabel = new Label("Title");
        gridPane.add(titleLabel, 1, 1);

        titleTextField = new TextField();
        gridPane.add(titleTextField, 2, 1);

        authorLabel = new Label("Author");
        gridPane.add(authorLabel, 3, 1);

        authorTextField = new TextField();
        gridPane.add(authorTextField, 4, 1);

        amountLabel = new Label("Amount");
        gridPane.add(amountLabel, 5, 1);

        amounTextField = new TextField();
        gridPane.add(amounTextField, 6, 1);

        priceLabel = new Label("Price");
        gridPane.add(priceLabel, 7, 1);

        priceTextField = new TextField();
        gridPane.add(priceTextField, 8, 1);

        saleTitleBookLabel = new Label("Sale book title");
        gridPane.add(saleTitleBookLabel, 1, 3);

        saleTitleBookTextField = new TextField();
        gridPane.add(saleTitleBookTextField, 2, 3);

        saleQuantityLabel = new Label("Quantity");
        gridPane.add(saleQuantityLabel, 3, 3);

        saleQuantityTextField = new TextField();
        gridPane.add(saleQuantityTextField, 4, 3);

        saveButton = new Button("Save");
        gridPane.add(saveButton, 1, 2);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 2, 2);

        saleButton = new Button("Sale");
        gridPane.add(saleButton, 5, 3);
    }

    private void initAdminOptions(GridPane gridPane) {
        usernameLabel = new Label("Username");
        gridPane.add(usernameLabel, 1, 4);

        usernameTextField = new TextField();
        gridPane.add(usernameTextField, 2, 4);

        passwordLabel = new Label("Password");
        gridPane.add(passwordLabel, 3, 4);

        passwordTextField = new TextField();
        gridPane.add(passwordTextField, 4, 4);

        registerUserButton = new Button("Add user");
        gridPane.add(registerUserButton, 5, 4);

        fireUserButton = new Button("Delete user");
        gridPane.add(fireUserButton, 6, 4);

        pdfButton = new Button("Generate Report");
        gridPane.add(pdfButton, 7, 4);
    }

    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener){
        saveButton.setOnAction(saveButtonListener);
    }

    public void addSelectionTableListener(ChangeListener selectionTableListener){
        bookTableView.getSelectionModel().selectedItemProperty().addListener(selectionTableListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener){
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addSaleButtonListener(EventHandler<ActionEvent> saleButtonListener){
        saleButton.setOnAction(saleButtonListener);
    }

    public void addRegisterUserListener(EventHandler<ActionEvent> registerUserButtonListener){
        registerUserButton.setOnAction(registerUserButtonListener);
    }

    public void addDeleteUserListener(EventHandler<ActionEvent> deleteUserButtonListener){
        fireUserButton.setOnAction(deleteUserButtonListener);
    }

    public void addGenerateReportButtonListener(EventHandler<ActionEvent> handler) {
        pdfButton.setOnAction(handler);
    }

    public void displayAlertMessage(String titleInformation, String headerInformation, String contextInformation){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleInformation);
        alert.setHeaderText(headerInformation);
        alert.setContentText(contextInformation);

        alert.showAndWait();
    }

    public String getTitle(){
        return titleTextField.getText();
    }

    public String getAuthor(){
        return authorTextField.getText();
    }

    public int getAmount(){
        return Integer.parseInt(amounTextField.getText());
    }

    public double getPrice(){
        return Double.parseDouble(priceTextField.getText());
    }

    public String getSaleTitleBook() {
        return saleTitleBookTextField.getText();
    }

    public int getSaleQuantity() {
        String quantityText = saleQuantityTextField.getText();
        if (quantityText == null || quantityText.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordTextField.getText();
    }

    public ObservableList<BookDTO> getBooksObservableList(){
        return booksObservableList;
    }

    public ObservableList<SaleDTO> getSaleObservableList(){
        return saleObservableList;
    }

    public ObservableList<UserDTO> getUsersObservabileList(){
        return usersObservabileList;
    }

    public void addBookToObservableList(BookDTO bookDTO){
        this.booksObservableList.add(bookDTO);
    }

    public void addUserToObservableList(UserDTO userDTO){
        this.usersObservabileList.add(userDTO);
    }

    public void updateBookToObservabileList(BookDTO bookDTO) {
        for (int i = 0; i < booksObservableList.size(); i++) {
            if (booksObservableList.get(i).getTitle().equals(bookDTO.getTitle())) {
                booksObservableList.set(i, bookDTO);
                break;
            }
        }
    }

    public void removeBookFromObservableList(BookDTO bookDTO){
        this.booksObservableList.remove(bookDTO);
    }

    public void removeUserFromObservableList(UserDTO userDTO){
        this.usersObservabileList.remove(userDTO);
    }

    public void addSaleToObservableList(SaleDTO saleDTO){
        this.saleObservableList.add(saleDTO);
    }

    public void removeSaleFromObservableList(SaleDTO saleDTO){
        this.saleObservableList.remove(saleDTO);
    }

    public TableView getBookTableView(){
        return bookTableView;
    }

    public TableView getSaleTableView(){
        return saleTableView;
    }

    public TableView getUserTableView(){
        return userTableView;
    }
}
