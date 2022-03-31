package com.example.tryout;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Stage_2Controller implements Initializable,TableInitializable,Exitable {

    @FXML
    public TextField titleField, categoryField, unitsField, expField;
    @FXML
    public Label errorLabel;
    @FXML
    public DatePicker datePicker;
    @FXML
    public TableView<Product> tableView;
    @FXML
    public TableColumn<Product, Integer> idCol, uniCol;
    @FXML
    public TableColumn<Product, String> titCol, catCol, bbCol;
    @FXML
    public ComboBox<String> opComboBox;
    @FXML
    public CheckBox idCheckBox, headerCheckBox;
    FileChooser fileChooser = new FileChooser();

    @Override
    public void initTable() throws SQLException {

        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        titCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        catCol.setCellValueFactory(new PropertyValueFactory<>("Category"));
        uniCol.setCellValueFactory(new PropertyValueFactory<>("Units"));
        bbCol.setCellValueFactory(new PropertyValueFactory<>("ExpDate"));
        //tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<Product> products = FXCollections.observableArrayList();
        Connection conn = DriverManager.getConnection(HelloApplication.url, HelloApplication.uname, HelloApplication.password);
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery("SELECT * FROM products;");
        while(res.next()){
            products.add(new Product(Integer.parseInt(res.getString(1)), res.getString(2), res.getString(3), Integer.parseInt(res.getString(4)), res.getString(5)));
        }
        tableView.setItems(products);
        conn.close();
    }
    public void initTableFromCsv(File file) throws FileNotFoundException, SQLException {
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            String[] strl = scanner.nextLine().split(",");
            Connection conn = DriverManager.getConnection(HelloApplication.url, HelloApplication.uname, HelloApplication.password);
            String command = "INSERT INTO Products(title,category,units,expirationdate) VALUES(?,?,?,?);";
            PreparedStatement st = conn.prepareStatement(command);
            st.setString(1, strl[0]);
            st.setString(2, strl[1]);
            st.setInt(3, Integer.parseInt(strl[2]));
            st.setString(4, strl[3]);
            st.executeUpdate();
        }
        initTable();
    }
    public void loadTable(ResultSet res) throws SQLException {
        ObservableList<Product> products = FXCollections.observableArrayList();
        while(res.next()){
            products.add(new Product(Integer.parseInt(res.getString(1)), res.getString(2), res.getString(3), Integer.parseInt(res.getString(4)), res.getString(5)));
        }
        tableView.setItems(products);
    }
    public void backButton_Click(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Stage_1.fxml")));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Product Manager");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    public void datePicker_Click(){
        expField.setText(datePicker.getValue().toString());
    }
    public void insertIntoProducts() throws SQLException{
        Connection conn = DriverManager.getConnection(HelloApplication.url, HelloApplication.uname, HelloApplication.password);
        String cmd = "INSERT INTO products(title,category,units,expirationdate) VALUES(?, ?, ?, ?);";
        PreparedStatement stmt = conn.prepareStatement(cmd);
        stmt.setString(1, titleField.getText());
        stmt.setString(2, categoryField.getText());
        stmt.setString(3, unitsField.getText());
        stmt.setString(4, expField.getText());
        stmt.executeUpdate();
        conn.close();
    }
    public void searchProducts(boolean[] qa) throws SQLException, EmptyDatabaseQueryException {
        StringBuilder query = new StringBuilder();
        boolean ok = false;
        query.append("SELECT * FROM Products WHERE ");
        if(qa[0]){
            query.append("title = ?");
            ok = true;
        }
        if(qa[1]) {
            if (ok)
                query.append(" AND Category = ?");
            else
                query.append(" category = ?");
            ok = true;
        }
        if(qa[2]) {
            if(ok)
                query.append(" AND units = ?");
            else
                query.append(" units = ?");
            ok = true;
        }
        if(qa[3]) {
            if(ok)
                query.append(" AND expirationdate = ?");
            else
                query.append(" expirationdate = ?");
            ok = true;
        }
        if(!ok)
            throw new EmptyDatabaseQueryException("Non-valid database query string");
        query.append(";");

        Connection conn = DriverManager.getConnection(HelloApplication.url, HelloApplication.uname, HelloApplication.password);
        PreparedStatement stmt = conn.prepareStatement(query.toString());
        int it = 0;

        if(qa[0])
            stmt.setString(++it, titleField.getText());
        if(qa[1])
            stmt.setString(++it, categoryField.getText());
        if(qa[2])
            stmt.setInt(++it, Integer.parseInt(unitsField.getText()));
        if(qa[3])
            stmt.setString(++it, expField.getText());

        loadTable(stmt.executeQuery());
    }
    public void deleteProducts(boolean[] qa) throws EmptyDatabaseCommandException, SQLException {
        StringBuilder query = new StringBuilder();
        boolean ok = false;
        query.append("DELETE FROM Products WHERE ");
        if(qa[0]){
            query.append("title = ?");
            ok = true;
        }
        if(qa[1]) {
            if (ok)
                query.append(" AND Category = ?");
            else
                query.append(" category = ?");
            ok = true;
        }
        if(qa[2]) {
            if(ok)
                query.append(" AND units = ?");
            else
                query.append(" units = ?");
            ok = true;
        }
        if(qa[3]) {
            if(ok)
                query.append(" AND expirationdate = ?");
            else
                query.append(" expirationdate = ?");
            ok = true;
        }
        if(!ok)
            throw new EmptyDatabaseCommandException("Non-valid database command string");
        query.append(";");

        Connection conn = DriverManager.getConnection(HelloApplication.url, HelloApplication.uname, HelloApplication.password);
        PreparedStatement stmt = conn.prepareStatement(query.toString());
        int it = 0;

        if(qa[0])
            stmt.setString(++it, titleField.getText());
        if(qa[1])
            stmt.setString(++it, categoryField.getText());
        if(qa[2])
            stmt.setInt(++it, Integer.parseInt(unitsField.getText()));
        if(qa[3])
            stmt.setString(++it, expField.getText());

        stmt.executeUpdate();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT id FROM Products;");
        int max = 0;
        while(rs.next()){
            if(Integer.parseInt(rs.getString(1)) > max)
                max = Integer.parseInt(rs.getString(1));
        }
        st.executeUpdate("ALTER TABLE Products AUTO_INCREMENT = " + max + ";");
    }
    public void saveProductstocsv(File file) throws SQLException, FileNotFoundException {
        Connection conn = DriverManager.getConnection(HelloApplication.url, HelloApplication.uname, HelloApplication.password);
        PrintWriter printer = new PrintWriter(file);
        if(headerCheckBox.isSelected()) {
            if(idCheckBox.isSelected())
                printer.write("ID,Title,Category,Units,Expiration Date\n");
            else
                printer.write("Title,Category,Units,Expiration Date\n");
        }
        Statement st = conn.createStatement();
        ResultSet res;
        if(idCheckBox.isSelected()) {
            res = st.executeQuery("SELECT * from products;");
            while(res.next()){
                printer.write(res.getString(1)+","+res.getString(2)+","+res.getString(3)+","+res.getString(4)+","+res.getString(5)+"\n");
            }
        }
        else {
            res = st.executeQuery("SELECT title,category,units,expirationdate from products;");
            while(res.next()){
                printer.write(res.getString(1)+","+res.getString(2)+","+res.getString(3)+","+res.getString(4)+"\n");
            }
        }
        printer.close();
    }
    public void exeButton_Click() throws SQLException, FileNotFoundException {
        switch(opComboBox.getValue()){
            case "Insert"->{
                boolean exceptionFlag = false;
                try {
                    insertIntoProducts();
                }catch (Exception e) {
                    errorLabel.setText("There are empty fields or wrong data\ntypes inserted. Insertion Failed.");
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setVisible(true);
                    exceptionFlag = true;
                    e.printStackTrace();
                }
                if(!exceptionFlag){
                    errorLabel.setText("Insertion Successful.");
                    errorLabel.setTextFill(Color.GREEN);
                    //errorLabel.setVisible(true);
                    Message msg = new Message(errorLabel, 4000);
                    msg.start();
                    initTable();
                }
            }
            case "Search"->{
                boolean[] qa = new boolean[4];
                if(!titleField.getText().equals(""))
                    qa[0] = true;

                if(!categoryField.getText().equals(""))
                    qa[1] = true;

                if(!unitsField.getText().equals(""))
                    qa[2] = true;

                if(!expField.getText().equals(""))
                    qa[3] = true;
                try {
                    try {
                        try {
                            searchProducts(qa);
                        }catch(SQLException e){
                            errorLabel.setText("Wrong Date format inserted\nSearch Failed.");
                            errorLabel.setTextFill(Color.RED);
                            Message msg = new Message(errorLabel, 4000);
                            msg.start();
                        }
                    }catch(NumberFormatException e){
                        errorLabel.setText("Wrong Data types inserted\nSearch Failed.");
                        errorLabel.setTextFill(Color.RED);
                        Message msg = new Message(errorLabel, 4000);
                        msg.start();
                        //e.printStackTrace();
                    }
                }catch(EmptyDatabaseQueryException e){
                    errorLabel.setText("All Search Fields are empty\nSearch Failed.");
                    errorLabel.setTextFill(Color.RED);
                    Message msg = new Message(errorLabel, 4000);
                    msg.start();
                    //e.printStackTrace();
                }
            }
            case "Delete"->{
                boolean[] da = new boolean[4];
                if(!titleField.getText().equals(""))
                    da[0] = true;

                if(!categoryField.getText().equals(""))
                    da[1] = true;

                if(!unitsField.getText().equals(""))
                    da[2] = true;

                if(!expField.getText().equals(""))
                    da[3] = true;
                try {
                    try {
                        try {
                            deleteProducts(da);
                        }catch(SQLException e){
                            errorLabel.setText("Wrong Date format inserted\nDeletion Failed.");
                            errorLabel.setTextFill(Color.RED);
                            Message msg = new Message(errorLabel, 4000);
                            msg.start();
                        }
                    }catch(NumberFormatException e){
                        errorLabel.setText("Wrong Data types inserted\nDeletion Failed.");
                        errorLabel.setTextFill(Color.RED);
                        Message msg = new Message(errorLabel, 4000);
                        msg.start();
                        //e.printStackTrace();
                    }
                }catch(EmptyDatabaseCommandException e){
                    errorLabel.setText("All Text Fields are empty\nDeletion Failed.");
                    errorLabel.setTextFill(Color.RED);
                    Message msg = new Message(errorLabel, 4000);
                    msg.start();
                    //e.printStackTrace();
                }
                initTable();
            }
            case "Refresh Table"-> initTable();

            case "Export to CSV"->{
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("csv files (*.csv)","*.csv"));
                File file = fileChooser.showSaveDialog(new Stage());
                if(file != null){
                    saveProductstocsv(file);
                }
                errorLabel.setText("Table exported successfully.");
                errorLabel.setTextFill(Color.GREEN);
                Message msg = new Message(errorLabel, 4000, 597, 320);
                msg.start();
            }
            case "Import from CSV"->{
                File file = fileChooser.showOpenDialog(new Stage());
                try {
                    initTableFromCsv(file);
                }catch(Exception e){
                    errorLabel.setText("CSV Data Format Not Valid\nImport Failed.");
                    errorLabel.setTextFill(Color.RED);
                    Message msg = new Message(errorLabel, 4000);
                    msg.start();
                }
            }
            case "Delete Table"->{
                Connection conn = DriverManager.getConnection(HelloApplication.url, HelloApplication.uname, HelloApplication.password);
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM Products;");
                stmt.executeUpdate("ALTER TABLE Products AUTO_INCREMENT = 1;");
                initTable();
                errorLabel.setText("Products Table Contents Deleted\nSuccessfully");
                errorLabel.setTextFill(Color.GREEN);
                Message msg = new Message(errorLabel, 4000);
                msg.start();
            }
        }
    }
    public void hideErrorLabel(){
        errorLabel.setVisible(false);
    }
    @Override
    public void exitButton_Click(){
        Platform.exit();
    }
    public void clearButton_Click(){
        titleField.setText("");
        categoryField.setText("");
        unitsField.setText("");
        expField.setText("");
    }
    public void opComboBox_Click(){
        if(opComboBox.getValue() != null) {
            errorLabel.setVisible(false);
            if (opComboBox.getValue().equals("Export to CSV")) {
                idCheckBox.setVisible(true);
                headerCheckBox.setVisible(true);
            }
            else{
                idCheckBox.setVisible(false);
                headerCheckBox.setVisible(false);
            }
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        opComboBox.getItems().addAll("Insert", "Search", "Delete", "Refresh Table", "Export to CSV", "Import from CSV", "Delete Table");
        fileChooser.setInitialDirectory(new File("..\\"));
    }
}
