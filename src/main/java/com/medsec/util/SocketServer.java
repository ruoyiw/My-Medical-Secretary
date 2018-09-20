package com.medsec.util;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class SocketServer implements Runnable {

    private ServerSocket serverSocket;

    public SocketServer(ServerSocket s) {
        this.serverSocket = s;
    }
    private static final String SERVER_KEY_STORE_PASSWORD = "123456";
    private static final String SERVER_TRUST_KEY_STORE_PASSWORD = "123456";
    private static final String SERVER_KEY_PATH = "/WEB-INF/classes/kserver.keystore";
    private static final String TRUST_CLIENT_KEY_PATH = "/WEB-INF/classes/tclient.keystore";


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

//            KeyStore ks = KeyStore.getInstance("JKS");
//            ks.load(new FileInputStream(SERVER_KEY_PATH), SERVER_KEY_STORE_PASSWORD.toCharArray());
//            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
//            kmf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());
//
//            KeyStore tks = KeyStore.getInstance("JKS");
//            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
//            tks.load(new FileInputStream(TRUST_CLIENT_KEY_PATH), SERVER_TRUST_KEY_STORE_PASSWORD.toCharArray());
//            tmf.init(tks);
//
//            SSLContext context = SSLContext.getInstance("SSL");
//            context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
//            ServerSocket s = context.getServerSocketFactory().createServerSocket(port);
            ServerSocket s = new ServerSocket(port);
            SocketServer socketServer = new SocketServer(s);
            Thread serverThread = new Thread(socketServer);
            serverThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
