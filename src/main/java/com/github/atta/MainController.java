package com.github.atta;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private JFXButton erasureBtn;

    @FXML
    private JFXButton exitBtn;

    @FXML
    private JFXTextArea inputArea;

    @FXML
    private JFXTextArea outputArea;

    @FXML
    void onErasureBtnClick(ActionEvent event) {

    }

    @FXML
    void onExitBtnClick(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
}
