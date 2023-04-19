package com.example.servingwebcontent.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class DateModel {

    @Value("${database.url}")
    private String url;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    public String getCurrentDate() throws Exception {
		// Load the JDBC driver class into memory
		Class.forName("com.mysql.jdbc.Driver");		
		
		//create a new connection object to database
        Connection conn = DriverManager.getConnection(url, username, password);
		
		//create statement obj
        Statement stmt = conn.createStatement();
		
		//execute query
        ResultSet rs = stmt.executeQuery("SELECT CURRENT_DATE()");
		
		//if rs.next() is true, return string
        if (rs.next()) {
            return rs.getString(1);
        } else {
            return null;
        }
    }
}
