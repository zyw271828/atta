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
        // TODO: Show progress bar

        String inputText = inputArea.getText();
        String translatedText = Translator.translate(inputText);
        String invTranslatedText = InvTranslator.invTranslate(translatedText);
        String fixInfo = Checker.check(inputText, invTranslatedText);
        String fixedText = Fixer.fix(invTranslatedText, fixInfo);
        String validationResult = Verifier.verify(inputText, fixedText);

        outputArea.setText(fixedText);

        // TODO: Display validationResult in a TextArea
        System.out.println(validationResult);
    }

    @FXML
    void onExitBtnClick(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
}
