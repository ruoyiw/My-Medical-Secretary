package com.medsec.util;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

/**
 * This class encapsulates all database queries.
 */
public class Database {
    private ServletContext sc;
    private DataSource dataSource;

    public Database(ServletContext sc) {
        this.sc = sc;
        dataSource = (DataSource) sc.getAttribute("dataSource");
    }

    // Database manipulating methods here

}