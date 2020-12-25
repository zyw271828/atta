package com.github.atta;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Arc;
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
    private Label checkLabel;

    @FXML
    private Label verifyLabel;

    @FXML
    private Arc checkArc;

    @FXML
    private Arc verifyArc;

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
                    String translatedText = Translator.translate(inputText, "en", "zh-CN");
                    String invTranslatedText = Translator.translate(translatedText, "zh-CN", "en");
                    String checkResult = Checker.check(inputText, invTranslatedText);
                    String validationResult = Verifier.verify(inputText, invTranslatedText);

                    final double checkScore = Double.parseDouble(checkResult);
                    final double validationScore = Double.parseDouble(validationResult);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            checkArc.setLength(360 * (checkScore / 100));
                            checkLabel.setText(String.format("%.2f", checkScore) + " %");
                            verifyArc.setLength(360 * (validationScore / 100));
                            verifyLabel.setText(String.format("%.2f", validationScore) + " %");
                        }
                    });

                    outputArea.setText(invTranslatedText);

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
