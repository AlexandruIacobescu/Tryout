package com.example.tryout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Register_StageController {

    @FXML
    public TextField unameField, eidField;
    @FXML
    public PasswordField passField, cpassField;
    @FXML
    Label controlLabel;

    public boolean fieldsOK() throws SQLException{
        Connection conn = DriverManager.getConnection(HelloApplication.url, HelloApplication.uname, HelloApplication.password);
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(eid) FROM Employees WHERE eid = ?");
        ResultSet res;
        stmt.setString(1, eidField.getText());
        try {
            res = stmt.executeQuery();
        }catch(SQLIntegrityConstraintViolationException e){
            throw new SQLIntegrityConstraintViolationException(e.toString());
        }
        res.next();
        if(unameField.getText().equals("")) {
            passField.setText("");
            cpassField.setText("");
            unameField.setText("");
            eidField.setText("");
            controlLabel.setVisible(true);
            controlLabel.setText("Username field empty");
            Message msg = new Message(controlLabel, 7000);
            controlLabel.setTextFill(Color.RED);
            msg.start();
            return false;
        }
        if(passField.getText().length() < 8) {
            passField.setText("");
            cpassField.setText("");
            unameField.setText("");
            eidField.setText("");
            controlLabel.setVisible(true);
            controlLabel.setText("The Password has to be at least 8 characters long");
            controlLabel.setTextFill(Color.RED);
            Message msg = new Message(controlLabel, 7000);
            msg.start();
            return false;
        }
        if(unameField.getText().length() > 60) {
            passField.setText("");
            cpassField.setText("");
            unameField.setText("");
            eidField.setText("");
            controlLabel.setVisible(true);
            controlLabel.setText("Username length cannot be greater than 60");
            controlLabel.setTextFill(Color.RED);
            Message msg = new Message(controlLabel, 7000);
            msg.start();
            return false;
        }
        if(passField.getText().equals("") || cpassField.getText().equals("")){
            passField.setText("");
            cpassField.setText("");
            unameField.setText("");
            eidField.setText("");
            controlLabel.setVisible(true);
            controlLabel.setText("The Password has to be at least 8 characters long");
            controlLabel.setTextFill(Color.RED);
            Message msg = new Message(controlLabel, 7000);
            msg.start();
            return false;
        }
        if(passField.getText().length() > 60) {
            passField.setText("");
            cpassField.setText("");
            unameField.setText("");
            eidField.setText("");
            controlLabel.setVisible(true);
            controlLabel.setText("Password length cannot be greater than 60");
            controlLabel.setTextFill(Color.RED);
            Message msg = new Message(controlLabel, 7000);
            msg.start();
            return false;
        }
        if(!passField.getText().equals(cpassField.getText())){
            passField.setText("");
            cpassField.setText("");
            unameField.setText("");
            eidField.setText("");
            controlLabel.setVisible(true);
            controlLabel.setText("The Passwords do not match");
            Message msg = new Message(controlLabel, 7000);
            controlLabel.setTextFill(Color.RED);
            msg.start();
            return false;
        }
        if(!res.getString(1).equals("1")){
            passField.setText("");
            cpassField.setText("");
            unameField.setText("");
            eidField.setText("");
            controlLabel.setVisible(true);
            controlLabel.setText("Employee ID not found in database\nReport to manager");
            controlLabel.setTextFill(Color.RED);
            Message msg = new Message(controlLabel, 7000);
            msg.start();
            return false;
        }
        return true;
    }
    public void createButton_Click() throws SQLException {
        try {
            if (fieldsOK()) {
                Connection conn = DriverManager.getConnection(HelloApplication.url, HelloApplication.uname, HelloApplication.password);
                PreparedStatement st = conn.prepareStatement("INSERT INTO Accounts VALUES(?,?,?)");
                st.setString(1, eidField.getText());
                st.setString(2, unameField.getText());
                st.setString(3, passField.getText());
                st.executeUpdate();
                passField.setText("");
                cpassField.setText("");
                unameField.setText("");
                eidField.setText("");
                controlLabel.setVisible(true);
                controlLabel.setText("Account Created Successfully");
                controlLabel.setTextFill(Color.GREEN);
                Message msg = new Message(controlLabel, 7000);
                msg.start();
            }
        }catch(SQLIntegrityConstraintViolationException e){
            passField.setText("");
            cpassField.setText("");
            unameField.setText("");
            eidField.setText("");
            controlLabel.setVisible(true);
            controlLabel.setText("This employee already has an account");
            controlLabel.setTextFill(Color.RED);
            Message msg = new Message(controlLabel, 7000);
            msg.start();
        }

    }
    public void backButton_Click(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Stage_1.fxml"));
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Products Manager Toolkit");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
