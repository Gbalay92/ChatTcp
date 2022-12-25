package com.gbl.chattcp;

import javafx.scene.control.TextArea;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClienteChat implements Runnable{
    private static final long serialVersionUID=1L;
    Socket socket=null;
    String nombre;
    DataInputStream receive;
    DataOutputStream send;
    TextArea chat;
    Boolean connected;

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public ClienteChat(Socket socket, String nombre, TextArea chat) {
        this.socket = socket;
        this.nombre = nombre;
        this.chat=chat;
        try {
            receive=new DataInputStream(socket.getInputStream());
            send=new DataOutputStream(socket.getOutputStream());
            send.writeUTF(nombre.toUpperCase() + " CONECTADO AL CHAT");

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        connected=true;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public DataInputStream getReceive() {
        return receive;
    }


    public void setReceive(DataInputStream receive) {
        this.receive = receive;
    }

    public DataOutputStream getSend() {
        return send;
    }

    public void setSend(DataOutputStream send) {
        this.send = send;
    }

    @Override
    public void run() {
        String text;
        while(connected){
            text="";
            try {
                text= receive.readUTF();
                //System.out.println(text);
                chat.appendText(text+"\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
