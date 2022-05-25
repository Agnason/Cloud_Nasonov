package com.example.cloud_application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChatController {
    @FXML
    public TextField textField;
    @FXML
    public ListView<String> listView;

    public void sendMessage(ActionEvent actionEvent) {
        String msg = textField.getText();
        textField.clear();
    }
}