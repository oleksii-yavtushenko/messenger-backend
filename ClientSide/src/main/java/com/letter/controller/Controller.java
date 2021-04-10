package com.letter.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

    @FXML
    public Button button1;

    @FXML
    public Button button2;


    public void click() {
        button2.setText("Clicked ones again!");
    }

    public void click1() {
        button2.setDisable(true);
    }
}
