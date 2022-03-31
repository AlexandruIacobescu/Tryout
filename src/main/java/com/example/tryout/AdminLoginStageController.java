package com.example.tryout;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminLoginStageController implements Initializable {
    @FXML
    public TextField unameTextField, passTextField;
    @FXML
    public Label errorLabel;

    public void loginButton_CLick(ActionEvent e) throws IOException {
        if(unameTextField.getText() != null && passTextField.getText() != null){
            if(unameTextField.getText().equals("overboss") && passTextField.getText().equals("admin")) {
                Parent root = FXMLLoader.load(getClass().getResource("Stage_3.fxml"));
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Administrative Options");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }
            else{
                unameTextField.setText("");
                passTextField.setText("");
                Message msg = new Message(errorLabel, 4000);
                msg.start();
            }
        }else{
            unameTextField.setText("");
            passTextField.setText("");
            Message msg = new Message(errorLabel, 4000);
            msg.start();
        }
    }
    public void backButton_CLick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Stage_1.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Products Manager Toolkit");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    public void exitButton_CLick(){
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(HelloApplication.params.length == 1 && HelloApplication.params[0].equals("debug")){
            unameTextField.setText("overboss");
            passTextField.setText("admin");
        }
    }
}
