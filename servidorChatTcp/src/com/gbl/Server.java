package com.gbl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final long serialVersionUID=1L;
    private static ServerSocket server;
    private static final int puerto=6000;
    static int conexiones=0;
    static int actuales=0;
    private static int maximo=10;
    static Socket tabla[]=new Socket[maximo];


    public Server() {

    }

    public static void main(String[] args) {
        try {
            server=new ServerSocket(puerto);
            System.out.println("server ON..");

            while(conexiones<maximo){
                Socket s=server.accept();
                tabla[conexiones]=s;
                conexiones++;
                actuales++;
                HiloServidor hilo =new HiloServidor(s);
                new Thread(hilo).start();
            }

            System.out.println("maximo de conexiones alcanzado..");
        } catch (IOException e) {
            System.out.println("error al iniciar server");        }

    }

}