package com.medsec.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogTest {

    private static final Logger logger= LogManager.getLogger(LogTest.class);

    public static void main(String[] args) {
        System.setProperty("java.util.logging.LogManager", "org.apache.logging.log4j.jul.LogManager");
        for(int i=0; i< 50; i++){
            logger.debug("debug test message"+i);
            logger.info("info test message"+i);
            logger.warn("warn test message"+i);
            logger.error("error test message"+i);
        }

    }

}
