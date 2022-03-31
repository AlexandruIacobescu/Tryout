package com.example.tryout;

import javafx.scene.control.Label;

public class Message extends Thread{
    private Label label;
    private int millis;
    private double x, y;
    private double initialX, initialY;
    private boolean move = false;
    Message(Label lblArg, int m){
        this.label = lblArg;
        this.millis = m;
    }
    Message(Label lblArg, int m, double x, double y){
        this.label = lblArg;
        this.millis = m;
        this.x=x;
        this.y=y;
        move = true;
    }
    private void showMessage() throws InterruptedException {
        label.setVisible(true);
        Thread.sleep(millis);
        label.setVisible(false);
    }
    private void moveLabel(){
        initialX = label.getLayoutX();
        initialY = label.getLayoutY();
        label.setLayoutX(x);
        label.setLayoutY(y);
    }
    @Override
    public void run(){
        if(move)
            moveLabel();
        try{
            showMessage();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        if(move) {
            label.setLayoutX(initialX);
            label.setLayoutY(initialY);
        }
    }
}
