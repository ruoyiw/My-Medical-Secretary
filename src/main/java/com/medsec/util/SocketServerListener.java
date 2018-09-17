package com.medsec.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SocketServerListener implements ServletContextListener {

    private static final int PORT = 11111;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Starting up!");
        try {
            SocketServer.init(PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
