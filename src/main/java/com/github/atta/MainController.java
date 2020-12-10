package com.github.atta;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        String inputText = inputArea.getText();

        if (inputText.equals("")) {
            inputArea.setPromptText("Please enter some text here.");
            return;
        } else {
            erasureBtn.setDisable(true);
            exitBtn.setDisable(true);

            Task<Boolean> task = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    // TODO: Show progress bar

                    String translatedText = Translator.translate(inputText);
                    String invTranslatedText = InvTranslator.invTranslate(translatedText);
                    String fixInfo = Checker.check(inputText, invTranslatedText);
                    String fixedText = Fixer.fix(invTranslatedText, fixInfo);
                    String validationResult = Verifier.verify(inputText, fixedText);

                    outputArea.setText(fixedText);

                    // TODO: Display validationResult in a TextArea
                    System.out.println(validationResult);

                    erasureBtn.setDisable(false);
                    exitBtn.setDisable(false);

                    return true;
                }
            };

            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent wse) {
                    if (task.getValue()) {

                    }
                }
            });

            new Thread(task).start();
        }
    }

    @FXML
    void onExitBtnClick(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
}
