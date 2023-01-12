package com.gbl.chattcp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    @FXML
    TextArea chat;
    @FXML
    TextField msg;
    @FXML
    Button send;
    @FXML
    Label user;
    ClienteChat client=null;
    int puerto=6000;




    public void onSendClick(ActionEvent actionEvent) {
        try {
            client.send.writeUTF(client.getNombre().toUpperCase()+ ": " +msg.getText());
            msg.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onExitClick(ActionEvent actionEvent){
        try {
            client.send.writeUTF("*");
            msg.setEditable(false);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        client.setConnected(false);
        showAlert(Alert.AlertType.INFORMATION, "Disconnected", "Se ha desconectado del servidor, porfavor cierre la ventana");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextInputDialog tid = new TextInputDialog("introduzca su nombre");
        String nombre= tid.showAndWait().get();
        //System.out.println(nombre);
        user.setText(nombre);
        try {
            Socket socket = new Socket("localhost", puerto);
            client=new ClienteChat(socket,nombre, chat);
            new Thread(client).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}