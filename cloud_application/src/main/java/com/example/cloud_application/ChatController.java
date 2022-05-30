package com.example.cloud_application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    private Network network;
    //lesson01//
    @FXML
    public ListView<String> clientView;
    @FXML
    public ListView<String> serverView;
    //lesson01//


    private void readLoop() {
        try {

            while (true) {
                String msg = network.readMessage();
                Platform.runLater(() -> {
                    serverView.getItems().add(msg);
                });
            }
        } catch (Exception e) {
            System.err.println("Connection lost");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // lesson 01 //
        Path path = Path.of("StorageClient/FileClient");
        clientView.getItems().clear();
        clientView.getItems().addAll(getFiles(path));
        // lesson 01 //

        try {
            network = new Network(8189);
            Thread readThread = new Thread(this::readLoop);
            readThread.setDaemon(true);
            readThread.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    //lesson01//
    public List<String> getFiles(Path dir) {
        String[] list = new File(String.valueOf(dir)).list();
        assert list != null;
        return Arrays.asList(list);
    }
    //lesson01//

    public void upload(ActionEvent actionEvent) {
    }

    public void download(ActionEvent actionEvent) {
    }
}