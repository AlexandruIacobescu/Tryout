package com.example.tryout;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController implements Initializable,Exitable {


    private Stage stage;
    private Scene scene;
    private Parent root;
    public final String uname = HelloApplication.uname, password = HelloApplication.password, url = HelloApplication.url;

    @FXML
    private TextField unameTextField, passTextField;
    @FXML
    private Label loginError;

    @Override
    public void exitButton_Click() {
        Platform.exit();
    }
    public void login(ActionEvent e) throws Exception {
        Connection conn = DriverManager.getConnection(url, uname, password);
        String queryStr = "SELECT COUNT(username) FROM Accounts WHERE Username = ? AND Password = ?;";
        PreparedStatement stmt = conn.prepareStatement(queryStr);
        stmt.setString(1, unameTextField.getText());
        stmt.setString(2, passTextField.getText());
        ResultSet res = stmt.executeQuery();
        res.next();
        if(res.getString(1).equals("1")) {
            root = FXMLLoader.load(getClass().getResource("Stage_2.fxml"));
            stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Product Manager");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        else{
            //loginError.setVisible(true);
            unameTextField.setText("");
            passTextField.setText("");
            Message msg = new Message(loginError, 4000);
            msg.start();
        }
    }

    public void errorLabelVisibilityChange() {
        loginError.setVisible(false);
    }
    public void adminButton_Click(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("adminLogin_Stage.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Administrator Login");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }
    public void registerButton_Click(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Register_Stage.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    public void databaseConnectionPopUp() throws IOException {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(HelloApplication.params.length == 1 && HelloApplication.params[0].equals("debug")) {
            unameTextField.setText("alex");
            passTextField.setText("admin");
        }
    }
}