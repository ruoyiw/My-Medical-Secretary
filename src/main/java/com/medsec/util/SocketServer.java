package com.medsec.util;


import java.io.FileInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class SocketServer implements Runnable {

    private ServerSocket serverSocket;

    public SocketServer(ServerSocket s) {
        this.serverSocket = s;
    }
    private static final String SERVER_KEY_STORE_PASSWORD = "server";
    private static final String SERVER_TRUST_KEY_STORE_PASSWORD = "server";
    private static final String SERVER_KEY_PATH = "/server_ks.jks";
    private static final String TRUST_CLIENT_KEY_PATH = "/clientTrust_ks.jks";


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

            KeyStore ks = KeyStore.getInstance("JKS");
            InputStream serverKey= SocketServer.class.getResourceAsStream(SERVER_KEY_PATH);
            ks.load(serverKey, SERVER_KEY_STORE_PASSWORD.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());

            KeyStore tks = KeyStore.getInstance("JKS");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            InputStream trustClientKey= SocketServer.class.getResourceAsStream(TRUST_CLIENT_KEY_PATH);
            tks.load(trustClientKey, SERVER_TRUST_KEY_STORE_PASSWORD.toCharArray());
            tmf.init(tks);

            SSLContext context = SSLContext.getInstance("SSL");
            context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            ServerSocket s = context.getServerSocketFactory().createServerSocket(port);
//            ServerSocket s = new ServerSocket(port);
            SocketServer socketServer = new SocketServer(s);
            Thread serverThread = new Thread(socketServer);
            serverThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
