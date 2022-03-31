package com.example.tryout;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionPopUp {
    @FXML
    TextField conUname, serverPort, connPass, ipAddr, schemaName;
    @FXML
    Button connectButton, testButton, exitButton;
    @FXML
    Label messageLabel;
    @FXML
    CheckBox saveBox;

    public boolean connectionOk(String uname, String password, String url){
        try{
            Connection conn = DriverManager.getConnection(url, uname, password);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private String getUrl(){
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:mysql://");
        sb.append(ipAddr.getText());
        sb.append(":" + serverPort.getText() + "/");
        sb.append(schemaName.getText());
        return sb.toString();
    }

    private String uname, password, url;

    public void exitButton_Click(){
        Platform.exit();
    }
    public void connectButton_Click(ActionEvent e) throws IOException {
        uname = conUname.getText();
        password = connPass.getText();
        url = getUrl();
        if(connectionOk(uname, password, url)){
            if(saveBox.isSelected()) {
                try {
                    PrintWriter printer = new PrintWriter(new FileOutputStream("database_connection_details.txt"));
                    printer.write(uname + "\n");
                    printer.write(password + "\n");
                    printer.write(url);
                    printer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            HelloApplication.uname = uname;
            HelloApplication.password = password;
            HelloApplication.url = url;
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Stage_1.fxml"));
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Products Manager Toolkit");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }catch(Exception ex){
                System.out.println(ex);
            }
        }
        else {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Connection : " + url + " failed.");
            Message msg = new Message(messageLabel, 12000);
            msg.start();
        }
    }
    public void testButton_Click(){
        uname = conUname.getText();
        password = connPass.getText();
        url = getUrl();
        if(connectionOk(uname, password, url)){
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setText("Connection : " + url + " Succeeded.");
            Message msg = new Message(messageLabel, 8000);
            msg.start();
        }
        else{
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Connection : " + url + " failed.");
            Message msg = new Message(messageLabel, 12000);
            msg.start();
        }
    }
}
