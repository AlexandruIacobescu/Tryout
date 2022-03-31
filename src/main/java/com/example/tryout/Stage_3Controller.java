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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class Stage_3Controller implements Initializable,TableInitializable,Exitable {
    @FXML
    private Button backButton, insertButton, exitButton, generateButton;
    @FXML
    private TableColumn<Employee, String> deptCol, eidCol, emailCol, fnmCol, phoneCol, ssnCol, lnmCol, hireCol;
    @FXML
    private DatePicker hireDatePicker;
    @FXML
    private TextField  emailField, empField, fnmField, phoneField, salField, ssnField, lnmField;
    @FXML
    private ComboBox<String> deptComboBox;
    @FXML
    private TableView<Employee> employeesTableView;
    @FXML
    private TableColumn<Employee, Double> salCol;
    @FXML
    private Label controlLabel;

    @Override
    public void initTable() throws SQLException{
        eidCol.setCellValueFactory(new PropertyValueFactory<>("eid"));
        fnmCol.setCellValueFactory(new PropertyValueFactory<>("fnm"));
        lnmCol.setCellValueFactory(new PropertyValueFactory<>("lnm"));
        ssnCol.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        hireCol.setCellValueFactory(new PropertyValueFactory<>("HireDate"));
        deptCol.setCellValueFactory(new PropertyValueFactory<>("dept"));
        salCol.setCellValueFactory(new PropertyValueFactory<>("sal"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        ObservableList<Employee> products = FXCollections.observableArrayList();
        Connection conn = DriverManager.getConnection(HelloApplication.url, HelloApplication.uname, HelloApplication.password);
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery("SELECT * FROM Employees;");
        while(res.next()){
            products.add(new Employee(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6),Double.parseDouble(res.getString(7)), res.getString(8), res.getString(9)));
        }
        employeesTableView.setItems(products);
        conn.close();
    }
    @Override
    public void exitButton_Click(){
        Platform.exit();
    }
    public void backButton_Click(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Stage_1.fxml")));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Products Manager Toolkit");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void generateCheck(){
        if(fnmField.getText() != null && lnmField.getText() != null && deptComboBox.getValue() != null){
            if(fnmField.getText().length() >= 2 && lnmField.getText().length() >= 2 && deptComboBox.getValue() != null){
                generateButton.setVisible(true);
            }
            else{
                generateButton.setVisible(false);
                empField.setText("");
            }
            if(generateButton.isVisible()){
                empField.setText("");
            }
        }
    }

    public void generateButton_CLick() throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(fnmField.getText().toCharArray()[0]).append(fnmField.getText().toCharArray()[1]);
        sb.append(lnmField.getText().toCharArray()[0]).append(lnmField.getText().toCharArray()[1]);
        sb.append(deptComboBox.getValue().toCharArray()[0]).append(deptComboBox.getValue().toCharArray()[1]);
        sb.append((int) ((Math.random() * (99999999 - 10000000)) + 10000000));
        empField.setText(sb.toString());
    }

    public void insertButton_Click() {
        try {
            Connection conn = DriverManager.getConnection(HelloApplication.url, HelloApplication.uname, HelloApplication.password);
            String[] dateStrList = hireDatePicker.getValue().toString().split("[-/. ]");
            String dateStr = dateStrList[0] + "-" + dateStrList[1] + "-" + dateStrList[2];
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Employees VALUES(?,?,?,?,?,?,?,?,?);");
            stmt.setString(1, empField.getText());
            stmt.setString(2, fnmField.getText());
            stmt.setString(3, lnmField.getText());
            stmt.setString(4, ssnField.getText());
            stmt.setString(5, dateStr);
            stmt.setString(6, deptComboBox.getValue());
            stmt.setString(7, salField.getText());
            stmt.setString(8, emailField.getText());
            stmt.setString(9, phoneField.getText());
            stmt.executeUpdate();
            initTable();
        }catch(Exception e){
            controlLabel.setText("Insertion failed for some reason");
            controlLabel.setTextFill(Color.RED);
            Message msg = new Message(controlLabel, 4000);
            msg.start();
            e.printStackTrace();
        }
    }
    public void refreshButton_Click() throws SQLException {
        initTable();
    }
    public void clearButton_Click(){
        fnmField.setText("");
        lnmField.setText("");
        ssnField.setText("");
        deptComboBox.setValue(null);
        hireDatePicker.setValue(null);
        salField.setText("");
        emailField.setText("");
        phoneField.setText("");
        empField.setText("");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            initTable();
        }catch(SQLException e){
            e.printStackTrace();
        }
        deptComboBox.getItems().addAll("Human Resources", "Management", "Reception", "Custody", "Grocery", "Frozen Foods", "Produce", "Dairy", "Deli", "Beer and Wine", "Meat","Health and Beauty", "Front End");
    }
}
