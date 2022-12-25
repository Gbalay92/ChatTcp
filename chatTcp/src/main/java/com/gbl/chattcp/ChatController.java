package com.gbl.chattcp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextInputDialog tid = new TextInputDialog("introduzca su nombre");
        String nombre= tid.showAndWait().get();
        //System.out.println(nombre);
        try {
            Socket socket = new Socket("localhost", puerto);
            client=new ClienteChat(socket,nombre, chat);
            new Thread(client).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}