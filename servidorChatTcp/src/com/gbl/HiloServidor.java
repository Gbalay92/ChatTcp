package com.gbl;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class HiloServidor implements Runnable{
    DataInputStream flujoEntrada;
    Socket socket=null;

    public HiloServidor(Socket s){
        this.socket=s;
        try {
            flujoEntrada=new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("error al recicibir datos");        }

    }
    private void sendMsg(String texto){
        for (int j = 0; j < Server.conexiones; j++) {
            Socket sl=Server.tabla[j];
            try {
                DataOutputStream flujoSalida=new DataOutputStream(sl.getOutputStream());
                flujoSalida.writeUTF(texto);
                //System.out.println(texto +"enviado");
            } catch (SocketException se){
                //capturando excepcion por si algunos de los clientes ha finzalizado...
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

    @Override
    public void run() {

            while(true){
                String cadena="";
                try {
                    cadena=flujoEntrada.readUTF();
                    if(cadena.trim().equals("*")){
                        Server.actuales--;
                        break;

                    }
                    sendMsg(cadena);
                    //System.out.println(cadena);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
    }
}
