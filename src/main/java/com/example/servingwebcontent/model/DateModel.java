package com.example.servingwebcontent.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;


@Component
public class DateModel {

    @Value("${database.url}")
    private String url;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;
	
    public List<String> getCurrentAndFutureDates() throws Exception {
		// Load the JDBC driver class into memory
		Class.forName("com.mysql.jdbc.Driver");
		
		//create a new connection object to database
        Connection conn = DriverManager.getConnection(url, username, password);
		
		//create statement obj
        Statement stmt = conn.createStatement();
		
		//create a list of string dates
        List<String> dates = new ArrayList<>();
		
		//execute query
        ResultSet rs = stmt.executeQuery("SELECT CURRENT_DATE()");
		
		/* if rs.next() is true, add rs as string to date obj
		*  attempt to get 30 days worth of dates using for loop
		*  get current date, add i day to it to get the interval date
		*/
        if (rs.next()) {
            dates.add(rs.getString(1));
            for (int i = 1; i <= 33; i++) {
                rs = stmt.executeQuery("SELECT DATE_ADD(CURRENT_DATE(), INTERVAL " + i + " DAY)");
                if (rs.next()) {
                    dates.add(rs.getString(1));
                }
				else{
					return null;
				}
            }
        } else {
			return null;
		}

        return dates;
    }	

	
    public String getCurrentDateTime() throws Exception {
		// Load the JDBC driver class into memory
		Class.forName("com.mysql.jdbc.Driver");		
		
		//create a new connection object to database
        Connection conn = DriverManager.getConnection(url, username, password);
		
		//create statement obj
        Statement stmt = conn.createStatement();
		
		//execute query
        ResultSet rs = stmt.executeQuery("SELECT NOW()");
		
		//if rs.next() is true, return string
        if (rs.next()) {
            return rs.getString(1);
        } else {
            return null;
        }
    }
	
}
