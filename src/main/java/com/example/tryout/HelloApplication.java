package com.example.tryout;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class HelloApplication extends Application{
    public static String[] params;
    public static String uname = "", password = "", url = "";
    public boolean DBConnectionOk() {
        Scanner scanner;
        try{
            scanner = new Scanner(new File("database_connection_details.txt"));
        }
        catch(FileNotFoundException e){
            return false;
        }
        try {
            if(uname.equals("") || password.equals("") || url.equals("")) {
                uname = scanner.nextLine();
                password = scanner.nextLine();
                url = scanner.nextLine();
            }
            Connection conn = DriverManager.getConnection(url, uname, password);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public void start(Stage stage) throws IOException {
        if(DBConnectionOk()) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Stage_1.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Products Manager Toolkit");
            stage.getIcons().add(new Image("H:\\IntelliJ\\Tryout\\pmt.png"));
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("databaseConnectionPopUp.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Connect to MySql Database");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        params = args;
        launch(args);
    }
}