package com.example.servingwebcontent.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateModel {

    @Value("${database.url}")
    private String url;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    @Value("${database2.url}")
    private String url2;

    @Value("${database2.username}")
    private String username2;

    @Value("${database2.password}")
    private String password2;	
	
	public List<String> getCurrentAndFutureDates() throws Exception {
		//create a list of string dates
		List<String> dates = new ArrayList<>();

		// Load the JDBC driver class into memory and create a new connection object to database
		try (Connection conn = DriverManager.getConnection(url2, username2, password2);
			 // create statement obj
			 Statement stmt = conn.createStatement()) {
			// execute query
			ResultSet rs = stmt.executeQuery("SELECT CURRENT_DATE()");

			/* if rs.next() is true, add rs as string to date obj
			*  attempt to get 30 days worth of dates using for loop
			*  get current date, add i day to it to get the interval date
			*/
			if (rs.next()) {
				dates.add(rs.getString(1));

				for (int i = 1; i <= 31; i++) {
					rs = stmt.executeQuery("SELECT DATE_ADD(CURRENT_DATE(), INTERVAL " + i + " DAY);");
					if (rs.next()) {
						dates.add(rs.getString(1));
					} else {
						return null;
					}
				}

			} else {
				return null;
			}
		}

		return dates;
	}
	

/*	
	public List<String> getCurrentAndFutureDates() throws Exception {
		// Load the JDBC driver class into memory
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		//create a new connection object to database
		Connection conn = DriverManager.getConnection(url2, username2, password2);
		
		//create statement obj
		Statement stmt = conn.createStatement();
		
		//create a list of string dates
		List<String> dates = new ArrayList<>();
		
		//execute query
		ResultSet rs = stmt.executeQuery("SELECT CURRENT_DATE()");
		*/
		
		/* if rs.next() is true, add rs as string to date obj
		*  attempt to get 30 days worth of dates using for loop
		*  get current date, add i day to it to get the interval date
		*
		*/
		
		/*
		if (rs.next()) {
			
			dates.add(rs.getString(1));
			
			for (int i = 1; i <= 31; i++) {
				rs = stmt.executeQuery("SELECT DATE_ADD(CURRENT_DATE(), INTERVAL " + i + " DAY);");
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
*/
	
	public String getCurrentDateTime() throws Exception {
		// Load the JDBC driver class into memory
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// Create a new connection object to database
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url2, username2, password2);

			// Create statement obj
			stmt = conn.createStatement();

			// Execute query
			rs = stmt.executeQuery("SELECT NOW()");

			// If rs.next() is true, return string
			if (rs.next()) {
				return rs.getString(1);
			} else {
				return null;
			}
		} finally {
			// Close ResultSet, Statement and Connection objects
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	
/*	
    public String getCurrentDateTime() throws Exception {
		// Load the JDBC driver class into memory
		Class.forName("com.mysql.cj.jdbc.Driver");		
		
		//create a new connection object to database
        Connection conn = DriverManager.getConnection(url2, username2, password2);
		
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
*/	
	public String checkMaxBookings(String selectedDate, String time) throws Exception {
		// Load the JDBC driver class into memory
		Class.forName("com.mysql.cj.jdbc.Driver");

		// Create a new connection object to database
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url2, username2, password2);

			// Create prepared statement obj with parameterized SQL SELECT statement
			String query = "SELECT COUNT(*) AS count FROM booking WHERE theDate = STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s')";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, selectedDate + " " + time);

			// Execute the prepared statement and get the result set
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int count = rs.getInt("count");
				if (count < 10) {
					return "possible";
				} else {
					return "not possible";
				}
			} else {
				return "possible";
			}
		} finally {
			// Close the result set, statement, and connection objects
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	
	/*
    public String checkMaxBookings(String selectedDate,String time) throws Exception {
		// Load the JDBC driver class into memory
		Class.forName("com.mysql.cj.jdbc.Driver");		
		
		//create a new connection object to database
        Connection conn = DriverManager.getConnection(url2, username2, password2);
		
		//create statement obj
        Statement stmt = conn.createStatement();
		
		//execute query
        ResultSet rs = stmt.executeQuery("SELECT * FROM booking WHERE theDate = STR_TO_DATE('" + selectedDate + " " + time + "', '%Y-%m-%d %H:%i:%s')");
		
		int count = 0;
		
		if (rs.next()){
			while (rs.next()) {
				count++;
			}
			if (count<10){
				//execute query
				return "possible";
			}
		}

		else{
			return "possible";
		}

		return "not possible";
    }
	*/	
	
	public String startBook(String selectedDate,String selectedTime,
	String selectedName,String selectedEmail,String selectedPhone,String selectedLocation) throws Exception{
		// Load the JDBC driver class into memory
		Class.forName("com.mysql.cj.jdbc.Driver");

		//create a new connection object to database
        Connection conn = DriverManager.getConnection(url, username, password);
		
		// Combine selectedDate and selectedTime into a LocalDateTime object
		LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(selectedDate), LocalTime.parse(selectedTime));

		// Convert LocalDateTime object to the appropriate format for your DATETIME column
		String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));		
		
		//create prepared statement obj with parameterized SQL INSERT statement
		PreparedStatement pstmt = conn.prepareStatement("INSERT INTO booking (name, email, phone, location, theDate) VALUES (?, ?, ?, ?, ?)");

		//set the values of the parameters in the SQL INSERT statement
		pstmt.setString(1, selectedName);
		pstmt.setString(2, selectedEmail);
		pstmt.setString(3, selectedPhone);
		pstmt.setString(4, selectedLocation);
		pstmt.setString(5, formattedDateTime);

		//execute the SQL INSERT statement
		pstmt.executeUpdate();
		
		//close the database connection
		pstmt.close();
		conn.close();

		return "Done";
	}
	
}
