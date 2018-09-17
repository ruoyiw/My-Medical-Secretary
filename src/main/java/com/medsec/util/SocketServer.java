package com.medsec.util;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Runnable {

    private ServerSocket serverSocket;

    public SocketServer(ServerSocket s) {
        this.serverSocket = s;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Socket sock = serverSocket.accept();
                SocketServerProcess s = new SocketServerProcess(sock);
                Thread socketThread = new Thread(s);
                socketThread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void init(int port) {
        try {
            ServerSocket s = new ServerSocket(port);
            SocketServer socketServer = new SocketServer(s);
            Thread serverThread = new Thread(socketServer);
            serverThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
